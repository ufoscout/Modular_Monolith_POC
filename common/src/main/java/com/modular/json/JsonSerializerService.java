package com.modular.json;

import java.io.OutputStream;

/**
 *
 * @author Francesco Cina'
 *
 */
public interface JsonSerializerService {

	/**
	 * Return the json representation of the Bean
	 * @param object
	 * @return
	 */
	String toJson(Object object);

	/**
	 * Return the json representation of the Bean
	 * @param object
	 */
	void toJson(Object object, OutputStream out);

	/**
	 * Return the json representation of the Bean
	 * WARN: it is slower than the other method!
	 * @param object
	 * @return
	 */
	String toPrettyPrintedJson(Object object);

	/**
	 * Return the json representation of the Bean
	 * WARN: it is slower than the other method!
	 * @param object
	 */
	void toPrettyPrintedJson(Object object, OutputStream out);

	/**
	 *
	 * Method to deserialize JSON content from given JSON content String.
	 *
	 * @param clazz
	 * @param json
	 * @return
	 */
	<T> T fromJson(String json, Class<T> clazz);

	/**
	 *
	 * Method to deserialize JSON content from given JSON content String of a parameterized type.
	 *
	 * @param json
	 * @param clazz
	 * @param genericsArgs
	 * @return
	 */
	<T> T fromJson(String json, Class<T> clazz, Class<?>... genericsArgs);

}
