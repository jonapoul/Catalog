@file:Suppress("RedundantVisibilityModifier")

package dev.jonpoulton.catalog.gradle.internal.writer

import com.google.common.truth.Truth.assertThat
import dev.jonpoulton.catalog.gradle.CatalogParameterNaming
import dev.jonpoulton.catalog.gradle.GenerateResourcesTask
import dev.jonpoulton.catalog.gradle.internal.ResourceEntry.XmlItem.WithArgs
import dev.jonpoulton.catalog.gradle.internal.StringArg
import dev.jonpoulton.catalog.gradle.test.isEqualToKotlin
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.nio.charset.Charset

class StringCatalogWriterTest {
  @get:Rule
  var folder = TemporaryFolder()

  private lateinit var codegenDestination: File
  private lateinit var codegenFile: File

  private fun stringCatalogWriter(
    packageName: String = "com.example",
    internal: Boolean = false,
    prefix: String = "",
    parameterNaming: CatalogParameterNaming = CatalogParameterNaming.Arg,
  ) = StringCatalogWriter(config = GenerateResourcesTask.TaskConfig(packageName, internal, prefix, parameterNaming))

  private val resources = listOf(
    WithArgs.String(
      file = File("."),
      name = "string_1",
      docs = "String 1 docs",
      args = emptyList(),
    ),
    WithArgs.String(
      file = File("."),
      name = "string_2",
      docs = null,
      args = listOf(
        StringArg(position = 1, type = 'd'),
        StringArg(position = 2, type = 'i'),
        StringArg(position = 3, type = 'u'),
        StringArg(position = 4, type = 'x'),
        StringArg(position = 5, type = 'o'),
      ),
    ),
    WithArgs.String(
      file = File("."),
      name = "string_3",
      docs = null,
      args = listOf(
        StringArg(position = 1, type = 'f'),
        StringArg(position = 2, type = 'e'),
        StringArg(position = 3, type = 'g'),
        StringArg(position = 4, type = 'a'),
        StringArg(position = 5, type = 's'),
      ),
    ),
    WithArgs.String(
      file = File("."),
      name = "string_4",
      docs = null,
      args = listOf(
        StringArg(position = 1, type = 'c'),
      ),
    ),
  )

  @Before
  fun setUp() {
    codegenDestination = folder.newFolder()
    codegenFile = File("${codegenDestination.absolutePath}/com/example/Strings.kt")
  }

  @Test
  fun `GIVEN defaults THEN generate public properties with no prefix`() {
    stringCatalogWriter().write(resources, codegenDestination)
    assertThat(
      codegenFile.readBytes().toString(Charset.defaultCharset()),
    ).isEqualToKotlin(
      """
        // DO NOT EDIT: Auto-generated by Catalog. https://github.com/jonapoul/catalog

        package com.example

        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.ReadOnlyComposable
        import androidx.compose.ui.res.stringResource
        import kotlin.Char
        import kotlin.Double
        import kotlin.Int
        import kotlin.String
        import kotlin.Suppress
        import kotlin.UInt

        public object Strings {
          /**
           * String 1 docs
           */
          public val string1: String
            @Composable
            @ReadOnlyComposable
            get() = stringResource(R.string.string_1)

          @Composable
          @ReadOnlyComposable
          public fun string2(
            arg1: Int,
            arg2: Int,
            arg3: UInt,
            arg4: UInt,
            arg5: UInt,
          ): String = stringResource(R.string.string_2, arg1, arg2, arg3, arg4, arg5)

          @Composable
          @ReadOnlyComposable
          public fun string3(
            arg1: Double,
            arg2: Double,
            arg3: Double,
            arg4: Double,
            arg5: String,
          ): String = stringResource(R.string.string_3, arg1, arg2, arg3, arg4, arg5)

          @Composable
          @ReadOnlyComposable
          public fun string4(arg1: Char): String = stringResource(R.string.string_4, arg1)
        }

      """,
    )
  }

  @Test
  fun `GIVEN internal THEN generate internal properties with no prefix`() {
    stringCatalogWriter(internal = true).write(resources, codegenDestination)
    assertThat(
      codegenFile.readBytes().toString(Charset.defaultCharset()),
    ).isEqualToKotlin(
      """
        // DO NOT EDIT: Auto-generated by Catalog. https://github.com/jonapoul/catalog

        package com.example

        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.ReadOnlyComposable
        import androidx.compose.ui.res.stringResource
        import kotlin.Char
        import kotlin.Double
        import kotlin.Int
        import kotlin.String
        import kotlin.Suppress
        import kotlin.UInt

        internal object Strings {
          /**
           * String 1 docs
           */
          internal val string1: String
            @Composable
            @ReadOnlyComposable
            get() = stringResource(R.string.string_1)

          @Composable
          @ReadOnlyComposable
          internal fun string2(
            arg1: Int,
            arg2: Int,
            arg3: UInt,
            arg4: UInt,
            arg5: UInt,
          ): String = stringResource(R.string.string_2, arg1, arg2, arg3, arg4, arg5)

          @Composable
          @ReadOnlyComposable
          internal fun string3(
            arg1: Double,
            arg2: Double,
            arg3: Double,
            arg4: Double,
            arg5: String,
          ): String = stringResource(R.string.string_3, arg1, arg2, arg3, arg4, arg5)

          @Composable
          @ReadOnlyComposable
          internal fun string4(arg1: Char): String = stringResource(R.string.string_4, arg1)
        }

      """,
    )
  }

  @Test
  fun `GIVEN prefix THEN generate public properties with prefix`() {
    stringCatalogWriter(prefix = "Sample").write(resources, codegenDestination)
    assertThat(
      codegenFile.readBytes().toString(Charset.defaultCharset()),
    ).isEqualToKotlin(
      """
        // DO NOT EDIT: Auto-generated by Catalog. https://github.com/jonapoul/catalog

        package com.example

        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.ReadOnlyComposable
        import androidx.compose.ui.res.stringResource
        import kotlin.Char
        import kotlin.Double
        import kotlin.Int
        import kotlin.String
        import kotlin.Suppress
        import kotlin.UInt

        public object SampleStrings {
          /**
           * String 1 docs
           */
          public val string1: String
            @Composable
            @ReadOnlyComposable
            get() = stringResource(R.string.string_1)

          @Composable
          @ReadOnlyComposable
          public fun string2(
            arg1: Int,
            arg2: Int,
            arg3: UInt,
            arg4: UInt,
            arg5: UInt,
          ): String = stringResource(R.string.string_2, arg1, arg2, arg3, arg4, arg5)

          @Composable
          @ReadOnlyComposable
          public fun string3(
            arg1: Double,
            arg2: Double,
            arg3: Double,
            arg4: Double,
            arg5: String,
          ): String = stringResource(R.string.string_3, arg1, arg2, arg3, arg4, arg5)

          @Composable
          @ReadOnlyComposable
          public fun string4(arg1: Char): String = stringResource(R.string.string_4, arg1)
        }

      """,
    )
  }

  @Test
  fun `GIVEN type-named args THEN generate public properties with no prefix`() {
    stringCatalogWriter(parameterNaming = CatalogParameterNaming.ByType)
      .write(resources, codegenDestination)
    assertThat(
      codegenFile.readBytes().toString(Charset.defaultCharset()),
    ).isEqualToKotlin(
      """
        // DO NOT EDIT: Auto-generated by Catalog. https://github.com/jonapoul/catalog

        package com.example

        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.ReadOnlyComposable
        import androidx.compose.ui.res.stringResource
        import kotlin.Char
        import kotlin.Double
        import kotlin.Int
        import kotlin.String
        import kotlin.Suppress
        import kotlin.UInt

        public object Strings {
          /**
           * String 1 docs
           */
          public val string1: String
            @Composable
            @ReadOnlyComposable
            get() = stringResource(R.string.string_1)

          @Composable
          @ReadOnlyComposable
          public fun string2(
            int1: Int,
            int2: Int,
            uint3: UInt,
            uint4: UInt,
            uint5: UInt,
          ): String = stringResource(R.string.string_2, int1, int2, uint3, uint4, uint5)

          @Composable
          @ReadOnlyComposable
          public fun string3(
            double1: Double,
            double2: Double,
            double3: Double,
            double4: Double,
            string5: String,
          ): String = stringResource(R.string.string_3, double1, double2, double3, double4, string5)

          @Composable
          @ReadOnlyComposable
          public fun string4(char1: Char): String = stringResource(R.string.string_4, char1)
        }

      """,
    )
  }
}
