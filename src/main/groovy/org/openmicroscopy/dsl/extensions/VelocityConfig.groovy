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
package org.openmicroscopy.dsl.extensions

import groovy.transform.CompileStatic
import org.apache.velocity.runtime.RuntimeConstants

@CompileStatic
class VelocityConfig {

    final Properties data = new Properties()

    void setProperty(String key, String value) {
        data.setProperty(key, value)
    }

    void setProperties(Map<String, String> propertyMap) {
        propertyMap.each { entry ->
            data.setProperty(entry.key, entry.value)
        }
    }

    void setResourceLoader(String resourceLoader) {
        data.setProperty(
                RuntimeConstants.RESOURCE_LOADER,
                resourceLoader
        )
    }

    void setFileResourceLoaderPath(String fileResourceLoaderPath) {
        data.setProperty(
                RuntimeConstants.FILE_RESOURCE_LOADER_PATH,
                fileResourceLoaderPath
        )
    }

    void setFileResourceLoaderCache(boolean fileResourceLoaderCache) {
        data.setProperty(
                RuntimeConstants.FILE_RESOURCE_LOADER_CACHE,
                fileResourceLoaderCache as String
        )
    }

    void setLoggerClassName(String loggerClassName) {
        data.setProperty(
                RuntimeConstants.RUNTIME_LOG_NAME,
                loggerClassName
        )
    }

    void setMaxNumberLoops(int max) {
        data.setProperty(
                RuntimeConstants.MAX_NUMBER_LOOPS,
                max as String
        )
    }

    void setSkipInvalidIterator(boolean skipInvalid) {
        data.setProperty(
                RuntimeConstants.SKIP_INVALID_ITERATOR,
                skipInvalid as String
        )
    }

    void setCheckEmptyObjects(boolean emptyCheck) {
        data.setProperty(RuntimeConstants.CHECK_EMPTY_OBJECTS, emptyCheck as String)
    }

    void setErrorMsgStart(String msgStart) {
        data.setProperty(
                RuntimeConstants.ERRORMSG_START,
                msgStart
        )
    }

    void setErrorMsgEnd(String msgEnd) {
        data.setProperty(
                RuntimeConstants.ERRORMSG_END,
                msgEnd
        )
    }

    void setParseDirectiveMaxdepth(int max) {
        data.setProperty(
                RuntimeConstants.DEFINE_DIRECTIVE_MAXDEPTH,
                max as String
        )
    }

    void setProvideScopeControl(boolean scope) {
        data.setProperty(
                RuntimeConstants.PROVIDE_SCOPE_CONTROL,
                scope as String
        )
    }

    VelocityConfig() {


        // Set default for velocity config
        setCheckEmptyObjects(false)
    }
}