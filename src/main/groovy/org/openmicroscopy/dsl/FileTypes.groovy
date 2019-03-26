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
package org.openmicroscopy.dsl

import groovy.transform.CompileStatic

@CompileStatic
class FileTypes {

    public static final String EXTENSION_OME_XML = "ome.xml"

    public static final String EXTENSION_DB_TYPE = "properties"

    public static final String EXTENSION_TEMPLATE = "vm"

    public static final String PATTERN_OME_XML = "**/*.$EXTENSION_OME_XML"

    public static final String PATTERN_DB_TYPE = "**/*-type*.$EXTENSION_DB_TYPE"

    public static final String PATTERN_TEMPLATE = "**/*.$EXTENSION_TEMPLATE"

}
