package com.chslcompany

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.File

class CleanDataStoreTestRule : TestWatcher() {
    override fun finished(description: Description) {
        super.finished(description)
        removeDataStoreFiles()
    }

    private fun removeDataStoreFiles() {
        InstrumentationRegistry.getInstrumentation().targetContext.run {
            File(filesDir, "user_preferences").deleteRecursively()
        }
    }
}