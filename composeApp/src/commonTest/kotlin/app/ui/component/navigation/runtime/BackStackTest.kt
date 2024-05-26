package app.ui.component.navigation.runtime

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class BackStackTest {
  @Test
  fun simpleTest() {
    val stack = mutableBackStackOf("🍇", "🍉", "🍌", "🍐", "🥝", "🍋")
    assertContentEquals(stack, listOf("🍇", "🍉", "🍌", "🍐", "🥝", "🍋"))
    assertEquals(stack.last(), "🍋")

    stack.push("🍍")
    assertContentEquals(stack, listOf("🍇", "🍉", "🍌", "🍐", "🥝", "🍋", "🍍"))

    stack.pop()
    assertContentEquals(stack, listOf("🍇", "🍉", "🍌", "🍐", "🥝", "🍋"))

    stack.popUntil { it == "🍐" }
    assertContentEquals(stack, listOf("🍇", "🍉", "🍌", "🍐"))

    stack.replace("🍓")
    assertContentEquals(stack, listOf("🍇", "🍉", "🍌", "🍓"))
    assertEquals(stack.canGoBack, true)

    stack.replaceAll("🍒")
    assertContentEquals(stack, listOf("🍒"))
    assertEquals(stack.canGoBack, false)
  }
}
