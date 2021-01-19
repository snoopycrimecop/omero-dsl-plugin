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
package org.openmicroscopy.dsl

import org.gradle.api.GradleException
import org.gradle.api.file.FileCollection
import org.gradle.api.file.FileTree
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.util.PatternSet

abstract class DslBase {

    private static final Logger Log = Logging.getLogger(DslBase)

    static File findDatabaseType(FileCollection collection, String type) {
        if (!type) {
            throw new GradleException("Database type (psql, sql, oracle, etc.) not specified")
        }

        File file = new File("$type-types.$FileTypes.EXTENSION_DB_TYPE")
        File databaseType = findInCollection(collection, file, FileTypes.PATTERN_DB_TYPE)
        if (!databaseType) {
            Set<File> files = getFiles(collection, "*resources*")
            if (files.isEmpty()) {
                databaseType = file
                Log.info("using reference file $databaseType")
            } else {
                throw new GradleException("Can't find $file in collection of database types")
            }
        } else {
            Log.info("Found database types file $databaseType")
        }
        return databaseType
    }

    static File findTemplate(FileCollection collection, File file) {
        if (!file) {
            throw new GradleException("No template (.vm) specified")
        }

        if (file.isAbsolute() && file.isFile()) {
            return file
        }
        Log.info("Searching for template with file name: $file")
        File template = findInCollection(collection, file, FileTypes.PATTERN_TEMPLATE)
        if (!template) {
            throw new GradleException("Can't find $file in collection of templates")
        }
        Log.info("Found template file $template")
        return template
    }

    static File findInCollection(FileCollection collection, File file, String include) {
        Set<File> files = getFiles(collection, include)
        Log.info("Looking for file with name $file.name from the following")
        files.find { File f ->
            Log.info("$f")
            f.name == file.name
        }
    }

    static Set<File> getFiles(FileCollection collection, String include) {
        if (collection.isEmpty()) {
            throw new GradleException("Collection is empty")
        }
        FileTree src = collection.asFileTree
        src.matching(new PatternSet().include(include)).files
    }

}
