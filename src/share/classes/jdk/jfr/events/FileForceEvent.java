/*
 * Copyright (c) 2017, 2019, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package jdk.jfr.events;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Name("com.oracle.jdk.FileForce")
@Label("File Force")
@Category("Java Application")
@Description("Force updates to be written to file")
public final class FileForceEvent extends AbstractJDKEvent {

    public static final ThreadLocal<FileForceEvent> EVENT =
        new ThreadLocal<FileForceEvent>() {
            @Override protected FileForceEvent initialValue() {
                return new FileForceEvent();
            }
        };

    @Label("Path")
    @Description("Full path of the file")
    public String path;

    @Label("Update Metadata")
    @Description("Whether the file metadata is updated")
    public boolean metaData;

    public void reset() {
        path = null;
        metaData = false;
    }
}
