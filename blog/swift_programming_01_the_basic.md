#Swift概要 The Basics


swift是apple主导开发的一门用于开发iOS和OS X软件的新的编程语言。尽管如此swift语言的许多部分都和c/c++相类似。  

swift提供了c/objective-c中所有的基础类型，包括整型Int，浮点数Double，Float，布尔型Bool，字符串String。另外还有两种主要的的集合类型，队列Array，字典Dictionary。除此之外swift还提供许多语言所没有的的元组tuples。  

常量和变量
定义常量用let声明，变量用var声明

 * let​ ​maximumNumberOfLoginAttempts​ = ​10
 * ​var​ ​currentLoginAttempt​ = ​0
 
 变量类型是可选的，写在变量明后，中间用冒号分割
 
 * ​var​ ​welcomeMessage​: ​String = “你好”
 
 打印数据使用println，在字符串内拼接变量可以用字符串插入法，将变量名作为占位符插入其中，需要（\）把变量用斜杠和括号包裹一下。
 
 * println(welcomeMessage)
 * println("输出内容\(welcomeMessage)")
 
 注释仍然是//和/** **/
 swift语句不需要分号来标识一个语句的结束，但是如果把多行语句写在一行，那分号还是需要的。
 整型数字可以写成不同的进制格式，加上进制所对应的前缀即可, 十进制的17可以也可以写成：
 
 * ob10001
 * 0o21
 * 0x11
 
 0b表示二进制, 0o表示八进制, 0x表示十六进制, 这里都是去了对应进制的英文单词首字母
 
 * binary
 * octal
 * hexadecimal
 
 但数据比较的时候，写成科学计数法也很常用，十进制和十六进制写指数时，有些区别，十进制的底数是10，十六进制的底数是2：
 
 * 1.25e2  ===> 1.25x100  ===> 125.0
 * 1.25e-2 ===> 1.25x0.01 ===> 0.0125
 * 0xFp2   ===> 15x2x2    ===> 60.0
 * 0xFp-2  ===> 15/2/2    ===> 3.75
 
 为了增加可读性在写一个很长的整型数据时，可以用下划线分割
 
 * 1000000 ===》 10_000_000