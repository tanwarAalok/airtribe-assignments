# JVM Basics

### JDK, JRE, and JVM
- **JDK (Java Development Kit)**: The full toolbox. It contains everything you need to write and compile Java code (Compiler, Debugger, and JRE).
- **JRE (Java Runtime Environment)**: The "Player." It provides the libraries and resources needed to *run* a Java program, but you cannot use it to write/compile code.
- **JVM (Java Virtual Machine)**: The "Engine." It is the actual process that executes the code on your specific computer hardware.

### What is Bytecode?
When you compile your `.java` files, they turn into `.class` files. These files contain **Bytecode**. Bytecode is an intermediate language that isn't quite human-readable and isn't quite machine-code yet. It is a universal language that the JVM understands.

### "Write Once, Run Anywhere" (WORA)
WORA is Java's core philosophy. Because Java compiles to **Bytecode** instead of specific machine code (like Windows or Mac code), the same `.class` file can be moved to any device.

As long as that device has a JVM installed, it can translate that universal Bytecode into its own local machine language. This removes the need for developers to rewrite code for different operating systems.