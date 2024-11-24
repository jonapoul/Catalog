package dev.jonpoulton.catalog.gradle.internal.writer

import com.google.common.truth.Truth.assertThat
import dev.jonpoulton.catalog.gradle.CatalogParameterNaming
import dev.jonpoulton.catalog.gradle.GenerateResourcesTask.TaskConfig
import dev.jonpoulton.catalog.gradle.internal.ResourceEntry
import dev.jonpoulton.catalog.gradle.test.isEqualToKotlin
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.nio.charset.Charset

class StringArrayCatalogWriterTest {
  @get:Rule
  var folder = TemporaryFolder()

  private lateinit var codegenDestination: File
  private lateinit var codegenFile: File

  private val resources = listOf(
    ResourceEntry.XmlItem.StringArray(file = File("."), name = "string_array_1", docs = "String array 1 docs"),
    ResourceEntry.XmlItem.StringArray(file = File("."), name = "string_array_2", docs = null),
  )

  private fun stringArrayCatalogWriter(
    packageName: String = "com.example",
    internal: Boolean = false,
    prefix: String = "",
    parameterNaming: CatalogParameterNaming = CatalogParameterNaming.Arg,
  ) = StringArrayCatalogWriter(config = TaskConfig(packageName, internal, prefix, parameterNaming))

  @Before
  fun setUp() {
    codegenDestination = folder.newFolder()
    codegenFile = File("${codegenDestination.absolutePath}/com/example/StringArrays.kt")
  }

  @Test
  fun `GIVEN defaults THEN generate public properties with no prefix`() {
    stringArrayCatalogWriter().write(resources, codegenDestination)
    assertThat(
      codegenFile.readBytes().toString(Charset.defaultCharset()),
    ).isEqualToKotlin(
      """
        // DO NOT EDIT: Auto-generated by Catalog. https://github.com/jonapoul/catalog

        package com.example

        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.ReadOnlyComposable
        import androidx.compose.ui.res.stringArrayResource
        import kotlin.Array
        import kotlin.String
        import kotlin.Suppress

        public object StringArrays {
          /**
           * String array 1 docs
           */
          public val stringArray1: Array<String>
            @Composable
            @ReadOnlyComposable
            get() = stringArrayResource(R.array.string_array_1)

          public val stringArray2: Array<String>
            @Composable
            @ReadOnlyComposable
            get() = stringArrayResource(R.array.string_array_2)
        }

      """,
    )
  }

  @Test
  fun `GIVEN internal THEN generate internal properties with no prefix`() {
    stringArrayCatalogWriter(internal = true).write(resources, codegenDestination)
    assertThat(
      codegenFile.readBytes().toString(Charset.defaultCharset()),
    ).isEqualToKotlin(
      """
        // DO NOT EDIT: Auto-generated by Catalog. https://github.com/jonapoul/catalog

        package com.example

        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.ReadOnlyComposable
        import androidx.compose.ui.res.stringArrayResource
        import kotlin.Array
        import kotlin.String
        import kotlin.Suppress

        internal object StringArrays {
          /**
           * String array 1 docs
           */
          internal val stringArray1: Array<String>
            @Composable
            @ReadOnlyComposable
            get() = stringArrayResource(R.array.string_array_1)

          internal val stringArray2: Array<String>
            @Composable
            @ReadOnlyComposable
            get() = stringArrayResource(R.array.string_array_2)
        }

      """,
    )
  }

  @Test
  fun `GIVEN prefix THEN generate public properties with prefix`() {
    stringArrayCatalogWriter(prefix = "Sample").write(resources, codegenDestination)
    assertThat(
      codegenFile.readBytes().toString(Charset.defaultCharset()),
    ).isEqualToKotlin(
      """
        // DO NOT EDIT: Auto-generated by Catalog. https://github.com/jonapoul/catalog

        package com.example

        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.ReadOnlyComposable
        import androidx.compose.ui.res.stringArrayResource
        import kotlin.Array
        import kotlin.String
        import kotlin.Suppress

        public object SampleStringArrays {
          /**
           * String array 1 docs
           */
          public val stringArray1: Array<String>
            @Composable
            @ReadOnlyComposable
            get() = stringArrayResource(R.array.string_array_1)

          public val stringArray2: Array<String>
            @Composable
            @ReadOnlyComposable
            get() = stringArrayResource(R.array.string_array_2)
        }

      """,
    )
  }
}
