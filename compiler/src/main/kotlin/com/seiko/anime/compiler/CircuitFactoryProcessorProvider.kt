package com.seiko.anime.compiler

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.seiko.anime.compiler.annotations.BindPresenter
import com.seiko.anime.compiler.annotations.BindUi
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.ksp.toAnnotationSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import com.squareup.kotlinpoet.withIndent
import kotlin.reflect.KClass

class CircuitFactoryProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    return CircuitFactoryProcessor(environment)
  }
}

class CircuitFactoryProcessor(environment: SymbolProcessorEnvironment) : SymbolProcessor {

  private val codeGenerator = environment.codeGenerator
  private val logger = environment.logger

  override fun process(resolver: Resolver): List<KSAnnotated> {
    val presenterVisitor = PresenterVisitor(
      codeGenerator = codeGenerator,
      logger = logger,
    )
    val presenters = resolver.getSymbolsWithAnnotation(BIND_PRESENTER_NAME)
      .filterIsInstance<KSFunctionDeclaration>()
      .toList()

    val uiVisitor = UiVisitor(
      codeGenerator = codeGenerator,
      logger = logger,
    )
    val uis = resolver.getSymbolsWithAnnotation(BIND_UI_NAME)
      .filterIsInstance<KSFunctionDeclaration>()
      .toList()

    presenters.forEach { it.accept(presenterVisitor, Unit) }
    uis.forEach { it.accept(uiVisitor, Unit) }

    generateBindPresenterComponent(presenters)
    generateUiComponent(uis)

    return emptyList()
  }

  inner class PresenterVisitor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
  ) : KSVisitorVoid() {

    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
      val annotation = function.findAnnotationType(BindPresenter::class)
      if (annotation == null) {
        logger.error("@BindPresenter annotation not found", function)
        return
      }

      val uiStateType = function.returnType?.resolve()?.declaration as? KSClassDeclaration
      if (uiStateType == null || uiStateType.isInterfaceFrom(VoyagerUiStateClassName)) {
        logger.error("@BindPresenter return type must be VoyagerUiState", function)
        return
      }

      val screenType = annotation.argumentWith("screen")?.toTypeName()

      val uiPresenterClassName = function.simpleName.asString() + "Wrapper"
      val uiPresenterFactoryClassName = function.simpleName.asString() + "Factory"

      FileSpec.builder(function.packageName.asString(), uiPresenterFactoryClassName)
        // generate class XXXPresenter
        .addType(
          TypeSpec.classBuilder(uiPresenterClassName)
            .addAnnotation(InjectAnnotation)
            .superclass(MoleculeUiPresenterClassName.parameterizedBy(uiStateType.toClassName()))
            .apply {
              val constructorParameters = mutableListOf<ParameterSpec>()
              function.parameters.forEach { parameter ->
                val parameterName = parameter.name?.asString().toString()
                val parameterType = parameter.type.toTypeName()
                constructorParameters.add(
                  ParameterSpec.builder(
                    parameterName,
                    parameterType,
                  ).build(),

                )
                addProperty(
                  PropertySpec.builder(parameterName, parameterType)
                    .apply {
                      parameter.annotations.forEach {
                        addAnnotation(it.toAnnotationSpec())
                      }
                    }
                    .initializer(parameterName)
                    .addModifiers(KModifier.PRIVATE)
                    .build(),
                )
              }
              if (constructorParameters.isNotEmpty()) {
                primaryConstructor(
                  FunSpec.constructorBuilder()
                    .addParameters(constructorParameters)
                    .build(),
                )
              }
            }
            .addFunction(
              FunSpec.builder("present")
                .addModifiers(KModifier.OVERRIDE)
                .addAnnotation(ComposableAnnotation)
                .returns(uiStateType.toClassName())
                .addCode(
                  buildCodeBlock {
                    add("return %L(\n", function.simpleName.asString())
                    withIndent {
                      function.parameters.forEach {
                        val parameterName = it.name?.asString().orEmpty()
                        addStatement("%L = %L,", parameterName, parameterName)
                      }
                    }
                    add(")")
                    // endControlFlow()
                    // addStatement("return state")
                  },
                )
                .build(),
            )
            .build(),
        )
        // generate XXXPresenterFactory
        .addType(
          TypeSpec.classBuilder(uiPresenterFactoryClassName)
            .addAnnotation(InjectAnnotation)
            .apply {
              val constructorParameters = mutableListOf<ParameterSpec>()
              function.parameters.forEach { parameter ->
                if (parameter.annotations.none {
                    it.annotationType.resolve().toTypeName() == AssistedAnnotation
                  }
                ) {
                  val parameterName = parameter.name?.asString().orEmpty()
                  val parameterType = parameter.type.toTypeName()
                  constructorParameters.add(
                    ParameterSpec.builder(
                      parameterName,
                      parameterType,
                    ).build(),
                  )
                  addProperty(
                    PropertySpec.builder(parameterName, parameterType)
                      .initializer(parameterName)
                      .addModifiers(KModifier.PRIVATE)
                      .build(),
                  )
                }
              }
              if (constructorParameters.isNotEmpty()) {
                primaryConstructor(
                  FunSpec.constructorBuilder()
                    .addParameters(constructorParameters)
                    .build(),
                )
              }
            }
            .addSuperinterface(UiPresenterFactoryClassName)
            .addFunction(
              FunSpec.builder("create")
                .addModifiers(KModifier.OVERRIDE)
                .addParameter("screen", ScreenProviderClassName)
                .addParameter("navigator", ProviderNavigatorClassName)
                // .addParameter("context", CircuitContextClassName)
                .returns(
                  UiPresenterClassName
                    .parameterizedBy(TypeVariableName("*"))
                    .copy(nullable = true),
                )
                .addCode(
                  buildCodeBlock {
                    beginControlFlow("return when (screen)")
                    withIndent {
                      beginControlFlow("is %L ->", screenType)
                      addStatement("%L(", uiPresenterClassName)
                      withIndent {
                        function.parameters.forEach { parameter ->
                          val parameterName = parameter.name?.asString().orEmpty()
                          if (parameter.annotations.none {
                              it.annotationType.resolve().toTypeName() == AssistedAnnotation
                            }
                          ) {
                            addStatement("%L = %L,", parameterName, parameterName)
                          } else {
                            when (parameter.type.resolve().toTypeName()) {
                              screenType -> addStatement(
                                "%L = screen,",
                                parameterName,
                              )

                              ProviderNavigatorClassName -> addStatement(
                                "%L = navigator,",
                                parameterName,
                              )

                              else -> {
                                logger.error("The parameter must be Screen or Navigator", parameter)
                              }
                            }
                          }
                        }
                      }
                      addStatement(")")
                      endControlFlow()
                      addStatement("else -> null")
                    }
                    endControlFlow()
                  },
                )
                .build(),
            )
            .build(),
        )
        .build().writeTo(
          codeGenerator,
          Dependencies(false, function.containingFile!!),
        )
    }
  }

  inner class UiVisitor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
  ) : KSVisitorVoid() {
    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
      val annotation = function.findAnnotationType(BindUi::class)
      if (annotation == null) {
        logger.error("@BindUi annotation not found", function)
        return
      }

      val uiStateType = function.parameters.firstOrNull()?.asClassDeclaration()
      if (uiStateType == null || uiStateType.isInterfaceFrom(VoyagerUiStateClassName)) {
        logger.error("@BindUi first parameter must be VoyagerUiState", function)
        return
      }

      val uiScreenClassName = function.simpleName.asString() + "Screen"
      val uiScreenFactoryClassName = function.simpleName.asString() + "ScreenFactory"

      FileSpec.builder(function.packageName.asString(), uiScreenFactoryClassName)
        // generate XXXUiScreen
        .addType(
          TypeSpec.classBuilder(uiScreenClassName)
            .addAnnotation(InjectAnnotation)
            .superclass(MoleculeUiScreenClassName.parameterizedBy(uiStateType.toClassName()))
            .addModifiers(KModifier.DATA)
            .apply {
              primaryConstructor(
                FunSpec.constructorBuilder()
                  .addParameter(
                    "provider",
                    ScreenProviderClassName,
                  )
                  .build(),
              )
              addProperty(
                PropertySpec.builder("provider", ScreenProviderClassName)
                  .initializer("provider")
                  .addModifiers(KModifier.OVERRIDE)
                  .build(),
              )
            }
            .addFunction(
              FunSpec.builder("Content")
                .addModifiers(KModifier.OVERRIDE)
                .addAnnotation(ComposableAnnotation)
                .addParameter("state", uiStateType.toClassName())
                .addParameter("navigator", NavigatorClassName)
                .addCode(
                  buildCodeBlock {
                    addStatement("%L(", function.simpleName.asString())
                    withIndent {
                      function.parameters.forEach { parameter ->
                        val parameterName = parameter.name?.asString().orEmpty()
                        when (parameter.type.resolve().toTypeName()) {
                          uiStateType.toClassName() -> addStatement("%L = state,", parameterName)
                          ModifierClassName -> addStatement("%L = %L", parameterName, ModifierClassName)
                          ProviderNavigatorClassName -> addStatement(
                            "%L = navigator,",
                            parameterName,
                          )

                          else -> {
                            logger.error("The parameter must be ${uiStateType.simpleName.asString()} or Modifier", parameter)
                          }
                        }
                      }
                    }
                    addStatement(")")
                  },
                )
                .build(),
            )
            .build(),
        )
        // generate XXXUiScreenFactory
        .addType(
          TypeSpec.classBuilder(uiScreenFactoryClassName)
            .addAnnotation(InjectAnnotation)
            .addSuperinterface(UiScreenFactoryClassName)
            .addFunction(
              FunSpec.builder("create")
                .addModifiers(KModifier.OVERRIDE)
                .addParameter("screen", ScreenProviderClassName)
                .returns(UiScreenClassName.copy(nullable = true))
                .addCode(
                  buildCodeBlock {
                    beginControlFlow("return when (screen)")
                    withIndent {
                      beginControlFlow(
                        "is %L -> ",
                        annotation.argumentWith("screen")?.toTypeName(),
                      )
                      addStatement("%L(screen)", uiScreenClassName)
                      endControlFlow()
                      addStatement("else -> null")
                    }
                    endControlFlow()
                  },
                )
                .build(),
            )
            .build(),
        )
        .build().writeTo(
          codeGenerator,
          Dependencies(false, function.containingFile!!),
        )
    }
  }

  private fun generateBindPresenterComponent(presenters: List<KSFunctionDeclaration>) {
    if (presenters.isEmpty()) return

    val className = "BindPresenterComponent"
    FileSpec.builder("app.inject", className)
      .addType(
        TypeSpec.interfaceBuilder(className)
          .apply {
            presenters.forEach { presenter ->
              addFunction(
                FunSpec.builder("bind${presenter}Factory")
                  .addAnnotation(IntoSetClassName)
                  .addAnnotation(ProvidesClassName)
                  .addAnnotation(ActivityScopeClassName)
                  .addParameter(
                    "factory",
                    ClassName(
                      presenter.packageName.asString(),
                      "${presenter.simpleName.asString()}Factory",
                    ),
                  )
                  .returns(UiPresenterFactoryClassName)
                  .addStatement("return factory")
                  .build(),
              )
            }
          }
          .build(),
      )
      .build().writeTo(
        codeGenerator,
        Dependencies(
          true,
          *(presenters.mapNotNull { it.containingFile }).toTypedArray(),
        ),
      )
  }

  private fun generateUiComponent(uis: List<KSFunctionDeclaration>) {
    if (uis.isEmpty()) return

    val className = "BindUiComponent"
    FileSpec.builder("app.inject", className)
      .addType(
        TypeSpec.interfaceBuilder(className)
          .apply {
            uis.forEach { ui ->
              addFunction(
                FunSpec.builder("bind${ui}ScreenFactory")
                  .addAnnotation(IntoSetClassName)
                  .addAnnotation(ProvidesClassName)
                  .addAnnotation(ActivityScopeClassName)
                  .addParameter(
                    "factory",
                    ClassName(
                      ui.packageName.asString(),
                      "${ui.simpleName.asString()}ScreenFactory",
                    ),
                  )
                  .returns(UiScreenFactoryClassName)
                  .addStatement("return factory")
                  .build(),
              )
            }
          }
          .build(),
      )
      .build().writeTo(
        codeGenerator,
        Dependencies(
          true,
          *(uis.mapNotNull { it.containingFile }).toTypedArray(),
        ),
      )
  }

  companion object {
    private val BIND_UI_NAME =
      requireNotNull(BindUi::class.qualifiedName) {
        "Can not get qualifiedName for @BindUi"
      }

    private val BIND_PRESENTER_NAME =
      requireNotNull(BindPresenter::class.qualifiedName) {
        "Can not get qualifiedName for @BindPresenter"
      }
  }
}

private fun <T : Any> KSAnnotated.findAnnotationType(annotationKClass: KClass<T>): KSAnnotation? {
  return annotations.find {
    it.annotationType.resolve().toTypeName() == annotationKClass.asTypeName()
  }
}

private fun KSAnnotation.argumentWith(name: String): KSType? {
  return arguments.find { it.name?.getShortName() == name }?.value as? KSType
}

private fun KSValueParameter.asClassDeclaration(): KSClassDeclaration? {
  return type.resolve().declaration as? KSClassDeclaration
}

private fun KSClassDeclaration.isInterfaceFrom(typeName: TypeName): Boolean {
  return superTypes.any {
    (it.resolve().declaration as? KSClassDeclaration)?.isInterfaceFrom(typeName)
      ?: (it.resolve().toTypeName() == typeName)
  }
}
