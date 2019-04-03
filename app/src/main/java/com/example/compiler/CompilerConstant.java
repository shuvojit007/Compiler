package com.example.compiler;


public class CompilerConstant {
//    public CompilerSample[] languageList;
//    private CompilerSample sample;


    public static String GetLanguage(String str) {
        switch (str) {
            case "C":
                return "#include <stdio.h>\n \nint main()\n{\n\tprintf(\"Hello World!\");\n\treturn 0;\n}\n ";

            case "C++":
                return "#include <iostream>\nusing namespace std;\n\nint main()\n{\n\tcout<<\"Hello World\";\n\n\treturn 0;\n}";

            case "Java 7.0":
                return "/* package whatever; // don't place package name! */\n\nimport java.util.*;\nimport java.lang.*;\nimport java.io.*;\n\n/* Name of the class has to be \"Main\" only if the class is public. */\nclass Main\n{\n\tpublic static void main (String[] args) throws java.lang.Exception\n\t{\n\t\tSystem.out.println(\"Hello World\");\n\t}\n}";

            case "Java":
                return "/* package whatever; // don't place package name! */\n\nimport java.util.*;\nimport java.lang.*;\nimport java.io.*;\n\n/* Name of the class has to be \"Main\" only if the class is public. */\nclass Main\n{\n\tpublic static void main (String[] args) throws java.lang.Exception\n\t{\n\t\tSystem.out.println(\"Hello World\");\n\t}\n}";

            case "Python 2":
                return "print (\"Hello, World!\")";

            case "Python 3":
                return "print (\"Hello, World!\")";

            case "Python":
                return "print (\"Hello, World!\")";
        }

        return null;
    }


//    public CompilerConstant(String str) {
//        languageList = new CompilerSample[24];
//        CompilerSample compilerSample = new CompilerSample("Shell Script", "shell", "default", "sh", ".sh", "echo \"Hello World\"");
//        int i = 0;
//        languageList[0] = compilerSample;
//        languageList[1] = new CompilerSample("C", "c", "default", "c", ".c", "#include <stdio.h>\n \nint main()\n{\n\tprintf(\"Hello World!\");\n\treturn 0;\n}\n ");
//        languageList[2] = new CompilerSample("C++", "c++", "default", "cpp", ".cpp", "#include <iostream>\nusing namespace std;\n\nint main()\n{\n\tcout<<\"Hello World\";\n\n\treturn 0;\n}");
//        languageList[3] = new CompilerSample("C# (C Sharp)", "cs", "default", "cs", ".cs", "using System;\n\npublic class Test\n{\n\tpublic static void Main()\n\t{\n\t\tConsole.WriteLine(\"Hello World\");\n\t}\n}");
//        languageList[4] = new CompilerSample("Clojure", "clojure", "default", "clj", ".clj", "(println \"Hello World\")");
//        languageList[5] = new CompilerSample("Go", "golang", "default", "go", ".go", "package main\nimport \"fmt\"\nfunc main() {\n\tfmt.Println(\"hello world\")\n}");
//        languageList[6] = new CompilerSample("Java 7.0", "java", "7", "java_7", ".java", "/* package whatever; // don't place package name! */\n\nimport java.util.*;\nimport java.lang.*;\nimport java.io.*;\n\n/* Name of the class has to be \"Main\" only if the class is public. */\nclass Demo\n{\n\tpublic static void main (String[] args) throws java.lang.Exception\n\t{\n\t\tSystem.out.println(\"Hello World\");\n\t}\n}");
//        languageList[7] = new CompilerSample("Java", "java", "8", "java_8", ".java", "/* package whatever; // don't place package name! */\n\nimport java.util.*;\nimport java.lang.*;\nimport java.io.*;\n\n/* Name of the class has to be \"Main\" only if the class is public. */\nclass Demo\n{\n\tpublic static void main (String[] args) throws java.lang.Exception\n\t{\n\t\tSystem.out.println(\"Hello World\");\n\t}\n}");
//        languageList[8] = new CompilerSample("SQL", "mysql", "default", "sql", ".sql", "create table myTable(name varchar(10));\ninsert into myTable values(\"Hello\");\nselect * from myTable;");
//        languageList[9] = new CompilerSample("Objective-C", "objectivec", "default", "m", ".m", "#include <Foundation/Foundation.h>\n\n@interface Test\n+ (const char *) classStringValue;\n@end\n\n@implementation Test\n+ (const char *) classStringValue;\n{\n    return \"Hello World\";\n}\n@end\n\nint main(void)\n{\n    printf(\"%s\", [Test classStringValue]);\n    return 0;\n}");
//        languageList[10] = new CompilerSample("Perl", "perl", "default", "pl", ".pl", "#!/usr/bin/perl\nprint \"Hello World\";\n");
//        languageList[11] = new CompilerSample("Php", "php", "default", "php", ".php", "<?php\n$ho = fopen('php://stdout', \"w\");\nfwrite($ho, \"Hello\");\nfclose($ho);\n");
//        languageList[12] = new CompilerSample("JavaScript(NodeJS)", "nodejs", "default", "js", ".js", "//Not happy with Plain JS? Use JS/HTML/CSS option for using your own libraries.\n\nconsole.log(\"Hello World\");");
//        languageList[13] = new CompilerSample("Python 2", "python", "default", "py_2", ".py", "print \"Hello, World!\"");
//        languageList[14] = new CompilerSample("Python 3", "python", "3.0", "py_3", ".py", "print (\"Hello, World!\")");
//        languageList[15] = new CompilerSample("R", "r", "default", "r", ".r", "print ( \"Hello World!\")");
//        languageList[16] = new CompilerSample("Ruby", "ruby", "default", "rb", ".rb", "puts \"Hello World\"");
//        languageList[17] = new CompilerSample("Scala", "scala", "default", "scala", ".scala", "object HelloWorld {\n  def main(args: Array[String]): Unit = {\n    println(\"Hello, world!\")\n  }\n}");
//        languageList[18] = new CompilerSample("Swift", "swift", "1.2", "swift_12", ".swift", "print(\"Hello Swift\")");
//        languageList[19] = new CompilerSample("VB.Net", "vb.net", "default", "vbs", ".vbs", "Imports System\n\nPublic Class Test\n\tPublic Shared Sub Main()\n\t\tConsole.writeLine(\"Hello World\")\n\tEnd Sub\nEnd Class");
//        languageList[20] = new CompilerSample("HTML", "html", "default", "html", ".html", "<!DOCTYPE html>\n<html>\n\t<head>\n\t\t<title>Page Title</title>\n\t</head>\n\t<body>\n\n\t\t<h1>This is a Heading</h1>\n\t\t<p>This is a paragraph.</p>\n\n\t</body>\n</html>");
//        languageList[21] = new CompilerSample("CSS", "css", "default", "css", ".html", "<!DOCTYPE html>\n<html>\n\t<head>\n\t\t<style>\n\t\t\tp.uppercase\n\t\t\t{\n\t\t\t\ttext-transform: uppercase;\n\t\t\t}\n\t\t\tp.lowercase\n\t\t\t{\n\t\t\t\ttext-transform: lowercase;\n\t\t\t}\n\t\t\tp.capitalize\n\t\t\t{\n\t\t\t\ttext-transform: capitalize;\n\t\t\t}\n\t\t</style>\n\t</head>\n\t<body>\n\n\t\t<p class=\"uppercase\">This is some text.</p>\n\t\t<p class=\"lowercase\">This is some text.</p>\n\t\t<p class=\"capitalize\">This is some text.</p>\n\n\t</body>\n</html>");
//        languageList[22] = new CompilerSample("JavaScript", "javascript", "default", "javascript", ".html", "<!DOCTYPE html>\n<html>\n\t<body>\n\n\t\t<h1>JavaScript CompilerResponse</h1>\n\n\t\t<script>\n\t\t\tdocument.write(\"<p>This is a paragraph</p>\");\n\t\t</script>\n\t</body>\n</html>");
//        languageList[23] = new CompilerSample("Asp.net", "asp", "default", "asp", ".asp", "<!DOCTYPE html>\n<html>\n\t<body>\n\n\t\t<h1>JavaScript CompilerResponse</h1>\n\n\t\t<script>\n\t\t\tdocument.write(\"<p>This is a paragraph</p>\");\n\t\t</script>\n\t</body>\n</html>");
//        this.languageList = languageList;
//        languageList = this.languageList;
//        int length = languageList.length;
//        while (i < length) {
//            CompilerSample compilerSample2 = languageList[i];
//            if (str.equalsIgnoreCase(compilerSample2.getLanguageName())) {
//                this.sample = compilerSample2;
//                return;
//            }
//            i++;
//        }
//    }
//
//    public String getHelloWorldCode() {
//        CompilerSample compilerSample = this.sample;
//        return compilerSample != null ? compilerSample.getExample() : "";
//    }
//
//    public String getLanguageCode() {
//        CompilerSample compilerSample = this.sample;
//        return compilerSample != null ? compilerSample.getServerLang() : "";
//    }
//
//    public String getLanguageVersion() {
//        CompilerSample compilerSample = this.sample;
//        return compilerSample != null ? compilerSample.getVersion() : "";
//    }
}
