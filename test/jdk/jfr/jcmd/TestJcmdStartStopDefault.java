/*
 * Copyright (c) 2015, 2019, Oracle and/or its affiliates. All rights reserved.
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

package jdk.jfr.jcmd;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdk.testlibrary.Asserts;
import jdk.testlibrary.jfr.FileHelper;
import jdk.testlibrary.OutputAnalyzer;
import jdk.testlibrary.jfr.JcmdAsserts;
import jdk.testlibrary.jfr.JcmdHelper;

/*
 * @test
 * @summary Start a recording without name.
 * @key jfr
 * @library /lib/testlibrary
 * @run main/othervm jdk.jfr.jcmd.TestJcmdStartStopDefault
 */
public class TestJcmdStartStopDefault {

    public static void main(String[] args) throws Exception {
        File recording = new File("TestJcmdStartStopDefault.jfr");

        OutputAnalyzer output = JcmdHelper.jcmd("JFR.start");
        JcmdAsserts.assertRecordingHasStarted(output);

        String name = parseRecordingName(output);
        name= "Recording-" + name;
        JcmdHelper.waitUntilRunning(name);

        output = JcmdHelper.jcmd("JFR.dump",
                "name=" + name,
                "filename=" + recording.getAbsolutePath());
        JcmdAsserts.assertRecordingDumpedToFile(output, name, recording);
        JcmdHelper.stopAndCheck(name);
        FileHelper.verifyRecording(recording);
    }

    private static String parseRecordingName(OutputAnalyzer output) {
        // Expected output:
        // Started recording recording-1. No limit (duration/maxsize/maxage) in use.
        // Use JFR.dump name=recording-1 filename=FILEPATH to copy recording data to file.

        String stdout = output.getStdout();
        Pattern p = Pattern.compile(".*Use JFR.dump name=(\\S+).*", Pattern.DOTALL);
        Matcher m = p.matcher(stdout);
        Asserts.assertTrue(m.matches(), "Could not parse recording name");
        String name = m.group(1);
        System.out.println("Recording name=" + name);
        return name;
    }
}
