package day07;
//学习地址：
import org.omg.Messaging.SyncScopeHelper;

/**
 * String 是不可变字符序列
 * final修饰，变量不可修改，方法不可重载，类不可继承
 * java 虚拟机将管辖的内存分为三个逻辑部分：方法区(Method Area)、Java栈(Stack)和Java堆(Heap)
 * 方法区包含常量池（方法区是静态分配的，编译器将变量在绑定在某个存储位置上，而且这些绑定不会在运行时改变。 常数池，源代码中的命名常量、String常量、代码段和数据段，静态域（用来存放static定义的静态成员）都保存在方法区。方法区是静态分配的，编译器将变量在绑定在某个存储位置上，而且这些绑定不会在运行时改变。 常数池，源代码中的命名常量、String常量、
 * 代码段和数据段，静态域（用来存放static定义的静态成员）都保存在方法区。）
 * 
 * 对于字符串，其对象的引用都是存储在栈中的，如果是编译期已经创建好的就存储在常量池中(如：直接用双引号定义的或者final修饰并且能在编译期就能确定的)，如果是运行期才能确定的就存储在堆中（如：new关键字创建出来的）。对于equals相等的字符串，在常量池中永远只有一份，在堆中有多份。

 * 
 * 数组的命名方式  int[] value   或者 int value []
 * @author 8150
 *
 */
public class TestString {

	private static final String String = null;

	public static void main(String[] args) {
		
		//#############################
	
			//第一个知识点：内存分析

			String s1="Hello";//先查询常量池中有没有此字符串，没有则创建一个。（###创建了对象）
			
			String s2="Hello";//先查询常量池中有没有此字符串，有这个字符串，不创建对象，直接调用
			
			String s3="Hello";//先查询常量池中有没有此字符串，有这个字符串，不创建对象，直接调用
			
			String s4=new String("Hello");//先查询常量池中有没有此字符串，有这个字符串，然后堆中在创建一个常量池中此对象的拷贝对象。
			
			String s5=new String("Hello");
			
			String s6=new  String("Hello");
			
			/**针对字符串
			 * 解释说明：
			 * s1,s2,s3在编译期就能确定，在创建对象时，先在常量池中寻找相同的值，若没有，则在常量池中创建一个对象。
			 * 对于通过new产生的一个字符串，会先去常量池中查找是否已经有了这个对象，如果没有则在常量池中创建一个次字符串的对象，然后堆中在创建一个常量池中此对象的拷贝对象。
			 */
			
			
			//第二个知识点：字符串扩充相关问题
			
			String a0="java";
			String a1=new String("java");
			String a2=new String("java");
			
			a2=a2.intern();//把常量池中的“java”的引用赋给了a2
			
			System.out.println(a0==a1);//结果：false 因为两个对象那个的地址不一样
			System.out.println(a0==a1.intern());//结果： true  因为a1的地址指向了a0的地址，两个对象是同一个对象
			System.out.println(a0==a2);//结果： true  因为a1的地址指向了a0的地址，两个对象是同一个对象
			
			
			//第三个知识点：String常量池问题的例子
			//第一个
			String b0="HelloWorld"; //在常量池中去查找，没有，在常量池中创建对象
			String b1="World";//在常量池中去查找，没有，在常量池中创建对象
			String b2="Hello"+b1;//在运行期重新分配了地址
			System.out.print(b0==b2);//false
			/*【1】中，JVM对于字符串引用，由于在字符串的”+”连接中，有字符串引用存在，而引用的值在程序编译期是无法确定的，
			即”Hello” + b1无法被编译器优化，只有在程序运行期来动态分配并将连接后的新地址赋给b2。
			所以上面程序的结果也就为false。*/
			//第二个
			String c0="Hello1World1";
			final String c1="world1";//在编译时被解析为常量值的一个本地拷贝存储到自己的常量池中或嵌入到它的字节码流中
			String c2="Hello1"+c1;
			System.out.println(c0==c2);//true
			/*【2】和【1】中唯一不同的是c1字符串加了final修饰，对于final修饰的变量，它在编译时被解析为常量值的一个本地拷贝存储到自己的常量池中或嵌入到它的字节码流中。
			所以此时的”Hello1” + c1和”Hello1” + “World1”效果是一样的。故上面程序的结果为true。*/
			//第三个
			String  d0="javaWorld2";
			final String d1=getWorld();
			String d2="java"+d1;
			System.out.println(d0==d2);//false
			public static String getWorld(){
				return "World2";
			}
			/*【3】JVM对于字符串引用s1，虽然它为final类型，但是它的值在编译期无法确定，
			只有在程序运行期调用方法后，将方法的返回值和”Hello”来动态连接并分配地址给s2，故上面程序的结果为false。
			 */
			
			//备注：方法是在运行期才会调用
			
			/*
			对于字符串，其对象的引用都是存储在栈中的，如果是编译期已经创建好的就存储在常量池中(如：直接用双引号定义的或者final修饰并且能在编译期就能确定的)，如果是运行期才能确定的就存储在堆中（如：new关键字创建出来的）。对于equals相等的字符串，在常量池中永远只有一份，在堆中有多份。
			————————————————
			版权声明：本文为CSDN博主「Season In The Sun」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
			原文链接：https://blog.csdn.net/sinat_37976731/article/details/79364781*/
			
//#################################################			
			//基本类型的内存解释
			
			//对于基本类型的变量和常量，变量和引用存储在栈中，常量存储在常量池中。
			
			int int1=9;//储存在栈
					
			final int INT1=9;//存储在常量池（因为final表示是常量）
			
//####################################################
			//基本类型和基本类型的包装类。  内存解析
			
			//基本类型有：byte、short、char、int、long、boolean。基本类型的包装类分别是：Byte、Short、Character、Integer、Long、Boolean（注意区分大小写）。 
			/*二者的区别是：基本类型体现在程序中是普通变量，基本类型的包装类是类，体现在程序中是引用变量。因此二者在内存中的存储位置不同：基本类型存储在栈中，而基本类型包装类存储在堆中。
			上边提到的这些包装类都实现了常量池技术，另外两种浮点数类型的包装类则没有实现。*/
			
			Integer i1=10;
			Integer i2=10;
			Integer i3=new Integer(10);
			Integer i4=new Integer(10);
			
			System.out.println(i1==i2);//true都在 常量池中，相同的字符串只会存在一个，指的地址相同，所以为true
			System.out.println(i2==i3);//false 指向的地址不同
			System.out.println(i3==i4);//false,都是new出来，地址不同，所以为false
			
			Double m1=1.0;
			Double m2=1.0;
			System.out.println(m1==m2);//false  但Double包装类没有实现常量池技术
			/*分析：

			i1和i2均是引用类型，在栈中存储指针，因为Integer是包装类。由于Integer包装类实现了常量池技术，因此i1和i2的10均是从常量池中获取的，均指向同一个地址，因此i1=12。

			i3和i4均是引用类型，在栈中存储指针，因为Integer是包装类。但是由于他们各自都是new出来的，因此不再从常量池寻找数据，而是从堆中各自new一个对象，然后各自保存指向对象的指针，所以i3和i4不相等，因为他们所存指针不同，所指向对象不同。

			m1和m2均是引用类型，在栈中存储指针，因为Double是包装类。但Double包装类没有实现常量池技术，因此Doubled1=1.0;相当于Double d1=new Double(1.0);，是从堆new一个对象，d2同理。因此d1和d2存放的指针不同，指向的对象不同，所以不相等。
			
			*/
			
//###############################################################
			//  ==和equals（）的用法
			
			/*== 是一个运算符

					Equals则是string对象的方法

					我们比较无非就是这两种：1、基本数据类型的比较 2、引用对象的比较

					基本数据类型比较
					==比较两个值是否相等，相等为true，否则为false；而equals()是String类的方法，不用来比较基本数据类型。

					引用对象比较
					==比较两个对象是否指向内存中的同一个对象，即指向同一个地址；而equals()则比较String对象的内容是否相同

					注意：

					string是一个特殊的引用类型。对于两个字符串的比较，不管是 == 和 Equals 这两者比较的都是字符串是否相同，例如下面的例子的输出结果都为true： 
					System.out.println(“Hello”==”Hello”); 
					System.out.println(“Hello”.equals(“Hello”));

					当你创建两个string对象时，内存中的地址是不相同的，你可以赋相同的值。所以字符串的内容相同，引用地址不一定相同（相同内容的对象地址不一定相同），但反过来，引用地址相同则内容一定相同。
					————————————————
					版权声明：本文为CSDN博主「Season In The Sun」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
					原文链接：https://blog.csdn.net/sinat_37976731/article/details/79364781
			*/
}
}