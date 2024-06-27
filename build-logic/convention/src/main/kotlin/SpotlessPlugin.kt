import com.diffplug.gradle.spotless.SpotlessExtension
import org.dreamerslab.newslayer.configureSpotless
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class SpotlessPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.diffplug.spotless")

            val spotlessExtension = extensions.getByType<SpotlessExtension>()
            configureSpotless(spotlessExtension)
        }
    }
}
