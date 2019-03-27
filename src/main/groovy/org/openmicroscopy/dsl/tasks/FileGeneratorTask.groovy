/*
 * -----------------------------------------------------------------------------
 *  Copyright (C) 2019 University of Dundee & Open Microscopy Environment.
 *  All rights reserved.
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
package org.openmicroscopy.dsl.tasks

import groovy.transform.CompileStatic
import ome.dsl.velocity.Generator
import ome.dsl.velocity.SingleFileGenerator
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputFile

@SuppressWarnings("UnstableApiUsage")
@CompileStatic
class FileGeneratorTask extends GeneratorBaseTask {

    /**
     * Set this when you only want to generate a single file
     */
    private final RegularFileProperty outputFile = project.objects.fileProperty()

    @Override
    protected Generator.Builder createGenerator() {
        return new SingleFileGenerator.Builder()
                .setOutFile(outputFile.get().asFile)
    }

    @OutputFile
    RegularFileProperty getOutputFile() {
        return outputFile
    }
}
