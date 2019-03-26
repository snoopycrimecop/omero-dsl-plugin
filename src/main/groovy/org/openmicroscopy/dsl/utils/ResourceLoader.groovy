/*
 * -----------------------------------------------------------------------------
 *  Copyright (C) 2019 University of Dundee. All rights reserved.
 *
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * ------------------------------------------------------------------------------
 */
package org.openmicroscopy.dsl.utils

import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.util.GradleVersion

class ResourceLoader {

    static def loadFiles(Project project, List<String> fileList) {
        return fileList.collect {
            loadFile(project, it)
        }
    }

    static def loadFiles(Project project, String[] fileList) {
        return fileList.collect {
            loadFile(project, it)
        }
    }

    static def loadFile(Project project, String resFile) {
        if (GradleVersion.current() >= GradleVersion.version('4.8')) {
            def url = IOUtils.getResource("/" + resFile) // getResource(project, resFile)
            if (!url) {
                throw new GradleException("can't find file file ${resFile}")
            }
            return project.resources.text.fromUri(url.toURI()).asFile()
        } else {
            return loadFileOrExtract(project, resFile)
        }
    }

    static def loadFileOrExtract(Project project, String resourceFile) {
        def result = new File("${project.buildDir}/${resourceFile}")
        // Check if file exists in build directory
        if (!result.exists()) {
            // Copy it to the projects build directory
            def inputStream = IOUtils.getResourceAsStream("/${resourceFile}")
            FileUtils.copyInputStreamToFile(inputStream, result)
        }
        return result
    }

}
