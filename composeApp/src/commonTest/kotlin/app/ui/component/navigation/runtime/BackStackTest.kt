package app.ui.component.navigation.runtime

class BackStackTest {

  // @Test
  // fun sizeTest() {
  //   val stack = mutableBackStackOf<String>()
  //   assertEquals(stack.size, 0)
  //   stack.replaceAll(listOf("🍇", "🍉", "🍌", "🍐", "🥝", "🍋"))
  //   assertEquals(stack.size, 6)
  // }
  //
  // @Test
  // fun canPopTest() {
  //   val stack = mutableBackStackOf<String>()
  //   assertEquals(stack.canPop, false)
  //   stack.push("🍇")
  //   assertEquals(stack.canPop, false)
  //   stack.push("🍉")
  //   assertEquals(stack.canPop, true)
  // }
  //
  // @Test
  // fun pushTest() {
  //   val stack = mutableBackStackOf<String>()
  //   stack.push("🍇")
  //   assertContentEquals(stack, listOf("🍇"))
  //   stack.push("🍉")
  //   assertContentEquals(stack, listOf("🍇", "🍉"))
  // }
  //
  // @Test
  // fun popTest() {
  //   val stack = mutableBackStackOf("🍇", "🍉", "🍌", "🍐", "🥝", "🍋")
  //   assertEquals(stack.pop(), "🍋")
  //   assertContentEquals(stack, listOf("🍇", "🍉", "🍌", "🍐", "🥝"))
  //   assertEquals(stack.pop(), "🥝")
  //   assertEquals(stack.pop(), "🍐")
  //   assertEquals(stack.pop(), "🍌")
  //   assertContentEquals(stack, listOf("🍇", "🍉"))
  //   assertEquals(stack.pop(), "🍉")
  //   assertEquals(stack.pop(), null)
  // }
  //
  // @Test
  // fun popUntilTest() {
  //   val stack = mutableBackStackOf("🍇", "🍉", "🍌", "🍐", "🥝", "🍋")
  //   stack.popUntil { it == "🍐" }
  //   assertContentEquals(stack, listOf("🍇", "🍉", "🍌", "🍐"))
  //   stack.popUntil { true }
  //   assertContentEquals(stack, listOf("🍇", "🍉", "🍌", "🍐"))
  //   stack.popUntil { false }
  //   assertContentEquals(stack, listOf("🍇"))
  // }
  //
  // @Test
  // fun popUntilWithInclusiveTest() {
  //   val stack = mutableBackStackOf("🍇", "🍉", "🍌", "🍐", "🥝", "🍋")
  //   stack.popUntil(true) { it == "🍐" }
  //   assertContentEquals(stack, listOf("🍇", "🍉", "🍌"))
  //   stack.popUntil(true) { true }
  //   assertContentEquals(stack, listOf("🍇", "🍉"))
  //   stack.popUntil(true) { false }
  //   assertContentEquals(stack, listOf("🍇"))
  // }
  //
  // @Test
  // fun replaceTest() {
  //   val stack = mutableBackStackOf("🍇", "🍉", "🍌", "🍋")
  //   stack.replace("🍍")
  //   assertContentEquals(stack, listOf("🍇", "🍉", "🍌", "🍍"))
  //   stack.replace("🍓")
  //   assertContentEquals(stack, listOf("🍇", "🍉", "🍌", "🍓"))
  // }
  //
  // @Test
  // fun replaceAllTest() {
  //   val stack = mutableBackStackOf("🍇", "🍉", "🍌", "🍋")
  //   stack.replaceAll("🍒")
  //   assertContentEquals(stack, listOf("🍒"))
  //   stack.replaceAll(listOf("🍇", "🍉", "🍌", "🍋"))
  //   assertContentEquals(stack, listOf("🍇", "🍉", "🍌", "🍋"))
  // }
  //
  // @Test
  // fun simpleTest() {
  //   val stack = mutableBackStackOf("🍇", "🍉", "🍌", "🍐", "🥝", "🍋")
  //   assertContentEquals(stack, listOf("🍇", "🍉", "🍌", "🍐", "🥝", "🍋"))
  //   assertEquals(stack.last(), "🍋")
  //
  //   stack.push("🍍")
  //   assertContentEquals(stack, listOf("🍇", "🍉", "🍌", "🍐", "🥝", "🍋", "🍍"))
  //
  //   stack.pop()
  //   assertContentEquals(stack, listOf("🍇", "🍉", "🍌", "🍐", "🥝", "🍋"))
  //
  //   stack.popUntil { it == "🍐" }
  //   assertContentEquals(stack, listOf("🍇", "🍉", "🍌", "🍐"))
  //
  //   stack.replace("🍓")
  //   assertContentEquals(stack, listOf("🍇", "🍉", "🍌", "🍓"))
  //   assertEquals(stack.canPop, true)
  //
  //   stack.replaceAll("🍒")
  //   assertContentEquals(stack, listOf("🍒"))
  //   assertEquals(stack.canPop, false)
  // }
}
