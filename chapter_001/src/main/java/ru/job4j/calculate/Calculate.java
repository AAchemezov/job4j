package ru.job4j.calculate;
/**
* Пример "Hello world'a" на языке Java.
* Class Calculate решение задачи  урока 1.3. части 001 учебного курса на job4j.ru
* @author Andrey Chemezov
* @since 14.02.2019
*/
public class Calculate {
	
	/**
	* Точка входа в приложение. Выводит в консоль "Hello World".
	* @param args Массив строковых параметров.
	*/
	public static void main(String[] args) {
		System.out.println("Hello World");
	}
	
	/**
	* Method echo.
	* @param name Your name.
	* @return Echo plus your name.
	*/
	public String echo(String name) {
		return "Echo, echo, echo : " + name;
	}
}
