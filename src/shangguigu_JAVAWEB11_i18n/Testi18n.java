package shangguigu_JAVAWEB11_i18n;

import static org.junit.jupiter.api.Assertions.*;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import com.sun.org.apache.bcel.internal.generic.SALOAD;

class Testi18n {

	/**
	 * ResourceBundle：资源包类
	 * 1. 在类路径下需要有对应的资源文件：baseName.properties,其中baseName是基名(如：i18n)
	 * 2. 可以使用基名_语言代码_国家代码.properties来添加不同国家或者地区的资源文件(如：i18n_zh_CN.properties)
	 * 3. 要求所有的基名相同的资源文件的key必须一致
	 * 4. 可以调用ResourceBundle.getBundle(基名，Locale实例)来获取指定的ResourceBundle对象
	 * 5. 调用ResourceBundle对象的getString(String key)来获取对应资源文件的value值
	 * 6. 如果获取出来的value值乱码，需要将value先用ISO-859-1进行解码，然后使用utf-8编码 
	 * 7. 最后可以结合DateFormat，NumberFormat，MessageFormat来实现国际化
	 * 8. 总结：文本信息通过properties文件以键值对的形式进行存储，读取它的ResourceBundle对象根据基名和Locale对象
	 * 	  找到对应国家或地区的properties文件；如果是日期，金钱，数字等敏感信息则要通过DateFormat，NumberFormat类
	 * 	 进行对应的国际化处理，最后通过MessageFormat类将文本信息和敏感信息组合在一起即可
	 * 
	 * 关于properties文件的乱码问题：
	 * 首先系统读取properties文件都是用的iso-8859-1编码格式
	 * 如果我们设置properties以utf-8编辑，可以正常显示中文，但是读取的时候需要先用iso-8859-1解码，然后再用utf-8编码才不会乱码
	 * 如果我们设置properties以iso-8859-1编辑，中文将被显示为ascii码，看起来不直观，但是读取的时候不做任何操作即可
	 * 在作javaweb的国际化的时候，尽量将properties文件设置为iso-8859-1，否则用fmt标签显示的时候不好处理；最好的做法还是全局设置utf-8，
	 * 指定的properties文件使用iso-8859-1即可
	 */
	@Test
	void testResourceBundle() throws UnsupportedEncodingException {
		Locale locale = Locale.CHINA;
		ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n", locale);
		String dateStr = resourceBundle.getString("date");
		//因为默认读取properties文件用的是ISO-8859-1的编码格式，所以需要先用iso-8859-1进行解码，然后utf-8编码，
		//这样子才不会乱码
//		dateStr = new String(dateStr.getBytes("iso-8859-1"), "utf-8");
		String salary = resourceBundle.getString("salary");
//		salary = new String(salary.getBytes("iso-8859-1"), "utf-8");
//		System.out.println(dateStr+"------"+salary);
		
		String str = "{0}:{1},{2}:{3}";
		double salStr = 12354.2;
		Date date = new Date();
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
		String sal = numberFormat.format(salStr);
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, locale);
		String dateString = dateFormat.format(date);
		String result = MessageFormat.format(str, dateStr,dateString,salary,sal);
		System.out.println(result);
		
	}

	/**
	 * MessageFormat:可以格式化模式字符串 模式字符串：带占位符的字符串："date:{0},sal:{1}"
	 * 可以通过format方法来对模式字符串进行格式化，前提是占位符表示的内容已经格式化好了
	 * 
	 */
	@Test
	void testMessageFormat() {
		String str = "date:{0},sal:{1}";
		Locale locale = Locale.CHINA;
		Date date = new Date();
		double d = 12234.23;

		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
		String dateStr = dateFormat.format(date);
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
		String salStr = numberFormat.format(d);

		str = MessageFormat.format(str, dateStr, salStr);
		System.out.println(str);
	}

	/**
	 * NumberFormat: 格式化数字到字符串，或货币字符串的工具类 1. 通过工厂方法获取到NumberFormat对象
	 * NumberFormat.getNumberInstance(Locale)：格式化为数字的字符串
	 * NumberFormat.getCurrencyInstance(Locale):格式化为货币的字符串
	 * 
	 * 通过format方法来进行格式化，将Number类型转换为String类型 通过parse方法把一个字符串解析为Number类型
	 */
	@Test
	void testNumberFormat() throws ParseException {
		double d = 23232.232;
		Locale locale = Locale.CHINA;

		NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
		String str = numberFormat.format(d);
		System.out.println(str); // 23,232.232

		NumberFormat numberFormat2 = NumberFormat.getCurrencyInstance(locale);
		str = numberFormat2.format(d);
		System.out.println(str); // ￥23,232.23

		str = "23,232.232";
		Number parse = numberFormat.parse(str);
		System.out.println(parse); // 23232.232

		str = "￥23,232.23";
		Number parse2 = numberFormat2.parse(str);
		System.out.println(parse2); // 23232.23

	}

	/**
	 * DateFormat:格式化日期的工具类，本身其实是一个抽象类
	 * 
	 * 1. 若希望通过DateFormat来吧一个date对象格式化为字符串，可以通过DateFormat的工厂方法来获取DateFormat对象 2.
	 * 获取只格式化Date的DateFormat对象：getDateInstance(int style, Locale locale) 3.
	 * 获取只格式化time的DateFormat对象：getTimeInstance(int style, Locale locale) 4.
	 * 获取即格式化Date也格式化time的DateFormat对象：getDateTimeInstance(int dateStyle, int
	 * timeStyle, Locale locale) 5.
	 * 其中style可以取值为：DateFormat的常量：SHORT,MEDIUM,LONG,FULL,locale则是代表国家地区的对象 6.
	 * 通过DateFormat的format方法来解析date对象为字符串
	 * 
	 * 7. 如果将字符串的事件转换为date类型？ ① 获取到DateFormat的子类：simpleDateFormat对象：new
	 * SimpleDateFormat(String pattern) 其中pattern为日期，事件的格式：例如：yyyy-MM-dd hh:mm:ss ②
	 * 调用DateFormat的parse方法将指定格式的字符串转换为date
	 */
	@Test
	void testDateFormat2() throws ParseException {
		String str = "1993-11-19 12:15:32";
		// 将字符串形式的时间改为date类型
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = dateFormat.parse(str);
		System.out.println(date);
	}

	@Test
	void testDateFormat() {
		Locale locale = Locale.CHINA;
		Date date = new Date();
		System.out.println(date);

		// 获取DateFormat对象，对时间进行特定的格式化
		DateFormat format = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale);
		String str = format.format(date);
		System.out.println(str);

	}

	/**
	 * Locale:java中表示国家或地区的类，jdk中提供了很多常量供我们选择 也可以通过Locale(LanguageCode,
	 * countryCode)的方式来创建 在WEB应用中可以通过request.getLocale()方法来获取
	 */
	@Test
	void testLocale() {
		Locale locale = Locale.CHINA;
		System.err.println(locale.getLanguage());
		System.err.println(locale.getCountry());
		System.out.println(locale.getDisplayCountry());
		locale = new Locale("en", "US");
		System.out.println(locale.getCountry());
		System.out.println(locale.getDisplayCountry());
	}

}
