package dev.jonpoulton.catalog.gradle.internal.writer

import com.google.common.truth.Truth.assertThat
import dev.jonpoulton.catalog.gradle.CatalogParameterNaming
import dev.jonpoulton.catalog.gradle.GenerateResourcesTask
import dev.jonpoulton.catalog.gradle.internal.ResourceEntry
import dev.jonpoulton.catalog.gradle.test.isEqualToKotlin
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.nio.charset.Charset

class DrawableCatalogWriterTest {
  @get:Rule
  var folder = TemporaryFolder()

  private lateinit var codegenDestination: File
  private lateinit var codegenFile: File

  private val resources = listOf(
    ResourceEntry.Drawable(file = File("."), name = "drawable_1", ResourceEntry.Drawable.Type.BITMAP),
    ResourceEntry.Drawable(file = File("."), name = "drawable_2", ResourceEntry.Drawable.Type.ANIMATED_VECTOR),
  )

  private fun drawableCatalogWriter(
    packageName: String = "com.example",
    internal: Boolean = false,
    prefix: String = "",
    parameterNaming: CatalogParameterNaming = CatalogParameterNaming.Arg,
  ) = DrawableCatalogWriter(config = GenerateResourcesTask.TaskConfig(packageName, internal, prefix, parameterNaming))

  @Before
  fun setUp() {
    codegenDestination = folder.newFolder()
    codegenFile = File("${codegenDestination.absolutePath}/com/example/Drawables.kt")
  }

  @Test
  fun `GIVEN defaults THEN generate public properties with no prefix`() {
    drawableCatalogWriter().write(resources, codegenDestination)

    assertThat(
      codegenFile.readBytes().toString(Charset.defaultCharset()),
    ).isEqualToKotlin(
      """
        // DO NOT EDIT: Auto-generated by Catalog. https://github.com/jonapoul/catalog

        package com.example

        import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
        import androidx.compose.animation.graphics.res.animatedVectorResource
        import androidx.compose.animation.graphics.vector.AnimatedImageVector
        import androidx.compose.runtime.Composable
        import androidx.compose.ui.graphics.painter.Painter
        import androidx.compose.ui.res.painterResource
        import kotlin.OptIn
        import kotlin.Suppress

        public object Drawables {
          public val drawable1: Painter
            @Composable
            get() = painterResource(R.drawable.drawable_1)

          @OptIn(ExperimentalAnimationGraphicsApi::class)
          public val drawable2: AnimatedImageVector
            @Composable
            get() = AnimatedImageVector.animatedVectorResource(R.drawable.drawable_2)
        }

      """,
    )
  }

  @Test
  fun `GIVEN internal THEN generate internal properties with no prefix`() {
    drawableCatalogWriter(internal = true).write(resources, codegenDestination)

    assertThat(
      codegenFile.readBytes().toString(Charset.defaultCharset()),
    ).isEqualToKotlin(
      """
        // DO NOT EDIT: Auto-generated by Catalog. https://github.com/jonapoul/catalog

        package com.example

        import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
        import androidx.compose.animation.graphics.res.animatedVectorResource
        import androidx.compose.animation.graphics.vector.AnimatedImageVector
        import androidx.compose.runtime.Composable
        import androidx.compose.ui.graphics.painter.Painter
        import androidx.compose.ui.res.painterResource
        import kotlin.OptIn
        import kotlin.Suppress

        internal object Drawables {
          internal val drawable1: Painter
            @Composable
            get() = painterResource(R.drawable.drawable_1)

          @OptIn(ExperimentalAnimationGraphicsApi::class)
          internal val drawable2: AnimatedImageVector
            @Composable
            get() = AnimatedImageVector.animatedVectorResource(R.drawable.drawable_2)
        }

      """,
    )
  }

  @Test
  fun `GIVEN prefix THEN generate public properties with prefix`() {
    drawableCatalogWriter(prefix = "Sample").write(resources, codegenDestination)

    assertThat(
      codegenFile.readBytes().toString(Charset.defaultCharset()),
    ).isEqualToKotlin(
      """
        // DO NOT EDIT: Auto-generated by Catalog. https://github.com/jonapoul/catalog

        package com.example

        import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
        import androidx.compose.animation.graphics.res.animatedVectorResource
        import androidx.compose.animation.graphics.vector.AnimatedImageVector
        import androidx.compose.runtime.Composable
        import androidx.compose.ui.graphics.painter.Painter
        import androidx.compose.ui.res.painterResource
        import kotlin.OptIn
        import kotlin.Suppress

        public object SampleDrawables {
          public val drawable1: Painter
            @Composable
            get() = painterResource(R.drawable.drawable_1)

          @OptIn(ExperimentalAnimationGraphicsApi::class)
          public val drawable2: AnimatedImageVector
            @Composable
            get() = AnimatedImageVector.animatedVectorResource(R.drawable.drawable_2)
        }

      """,
    )
  }
}
