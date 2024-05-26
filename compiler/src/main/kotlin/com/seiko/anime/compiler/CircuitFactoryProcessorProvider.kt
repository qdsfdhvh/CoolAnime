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
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.seiko.anime.compiler.annotations.CircuitInject
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.buildCodeBlock
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
    val presenters = resolver.getSymbolsWithAnnotation(CIRCUIT_INJECT_NAME)
      .filterIsInstance<KSClassDeclaration>()
      .toList()

    val uiVisitor = UiVisitor(
      codeGenerator = codeGenerator,
      logger = logger,
    )
    val uis = resolver.getSymbolsWithAnnotation(CIRCUIT_INJECT_NAME)
      .filterIsInstance<KSFunctionDeclaration>()
      .toList()

    presenters.forEach { it.accept(presenterVisitor, Unit) }
    uis.forEach { it.accept(uiVisitor, Unit) }

    generateBindPresenterComponent(presenters)
    generateUiComponent(uis)

    return emptyList()
  }

  class PresenterVisitor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
  ) : KSVisitorVoid() {
    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
      val className = classDeclaration.simpleName.asString() + "Factory"
      val presenterFactoryName = "presenterFactory"
      val annotation = classDeclaration.findAnnotationType(CircuitInject::class)
      if (annotation == null) {
        logger.error("@BindPresenter annotation not found", classDeclaration)
        return
      }

      val screenType = annotation.argumentWith("screen")?.toTypeName()

      val params = mutableListOf<TypeName>()
      classDeclaration.primaryConstructor?.parameters?.let { parameters ->
        parameters.forEach { parameter ->
          if (parameter.annotations.none {
              it.annotationType.resolve().toTypeName() == AssistedAnnotation
            }
          ) {
            return@forEach
          }
          when (val type = parameter.type.toTypeName()) {
            screenType,
            NavigatorClassName,
            CircuitContextClassName,
            -> {
              params.add(type)
            }

            else -> Unit
          }
        }
      }

      FileSpec.builder(classDeclaration.packageName.asString(), className)
        .addType(
          TypeSpec.classBuilder(className)
            .addAnnotation(InjectAnnotation)
            .apply {
              val presenterFactoryType = LambdaTypeName.get(
                receiver = null,
                parameters = params.map {
                  ParameterSpec("", it)
                },
                returnType = classDeclaration.toClassName(),
              )
              primaryConstructor(
                FunSpec.constructorBuilder()
                  .addParameter(
                    ParameterSpec.builder(
                      presenterFactoryName,
                      presenterFactoryType,
                    ).build(),
                  )
                  .build(),
              )
              addProperty(
                PropertySpec.builder(presenterFactoryName, presenterFactoryType)
                  .initializer(presenterFactoryName)
                  .addModifiers(KModifier.PRIVATE)
                  .build(),
              )
            }
            .addSuperinterface(PresenterFactoryClassName)
            .addFunction(
              FunSpec.builder("create")
                .addModifiers(KModifier.OVERRIDE)
                .addParameter("screen", ScreenClassName)
                .addParameter("navigator", NavigatorClassName)
                .addParameter("context", CircuitContextClassName)
                .returns(
                  PresenterClassName
                    .parameterizedBy(TypeVariableName("*"))
                    .copy(nullable = true),
                )
                .addCode(
                  buildCodeBlock {
                    beginControlFlow("return when (screen)")
                    withIndent {
                      addStatement("is %L -> %N(", screenType, presenterFactoryName)
                      withIndent {
                        params.forEach { type ->
                          when (type) {
                            screenType -> addStatement("screen,")
                            NavigatorClassName -> addStatement("navigator,")
                            CircuitContextClassName -> addStatement("context,")
                            else -> Unit
                          }
                        }
                      }
                      addStatement(")")
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
          Dependencies(false, classDeclaration.containingFile!!),
        )
    }
  }

  class UiVisitor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
  ) : KSVisitorVoid() {
    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
      val className = function.simpleName.asString() + "Factory"

      val annotation = function.findAnnotationType(CircuitInject::class)
      if (annotation == null) {
        logger.error("@BindUi annotation not found", function)
        return
      }

      val uiStateType =
        function.parameters.firstOrNull()?.type?.resolve()?.declaration as? KSClassDeclaration
      if (uiStateType == null || uiStateType.superTypes.none {
          it.resolve().toClassName() == CircuitUiStateClassName
        }
      ) {
        logger.error("The first parameter must be CircuitUiState", function)
        return
      }

      FileSpec.builder(function.packageName.asString(), className)
        .addType(
          TypeSpec.classBuilder(className)
            .addAnnotation(InjectAnnotation)
            .addSuperinterface(UiFactoryClassName)
            .addFunction(
              FunSpec.builder("create")
                .addModifiers(KModifier.OVERRIDE)
                .addParameter("screen", ScreenClassName)
                .addParameter("context", CircuitContextClassName)
                .returns(
                  UiClassName
                    .parameterizedBy(TypeVariableName("*"))
                    .copy(nullable = true),
                )
                .addCode(
                  buildCodeBlock {
                    beginControlFlow("return when (screen)")
                    withIndent {
                      beginControlFlow(
                        "is %L -> ",
                        annotation.argumentWith("screen")?.toTypeName(),
                      )
                      beginControlFlow(
                        "%L<%L> { state, modifier ->",
                        uiLambdaClassName,
                        uiStateType.toClassName(),
                      )
                      addStatement("%L(state, modifier)", function.simpleName.asString())
                      endControlFlow()
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

  private fun generateBindPresenterComponent(presenters: List<KSClassDeclaration>) {
    if (presenters.isEmpty()) return

    val className = "BindPresenterComponent"
    FileSpec.builder("presentation.inject", className)
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
                  .returns(PresenterFactoryClassName)
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
    FileSpec.builder("presentation.inject", className)
      .addType(
        TypeSpec.interfaceBuilder(className)
          .apply {
            uis.forEach { ui ->
              addFunction(
                FunSpec.builder("bind${ui}Factory")
                  .addAnnotation(IntoSetClassName)
                  .addAnnotation(ProvidesClassName)
                  .addAnnotation(ActivityScopeClassName)
                  .addParameter(
                    "factory",
                    ClassName(
                      ui.packageName.asString(),
                      "${ui.simpleName.asString()}Factory",
                    ),
                  )
                  .returns(UiFactoryClassName)
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
    private val CIRCUIT_INJECT_NAME =
      requireNotNull(CircuitInject::class.qualifiedName) {
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
