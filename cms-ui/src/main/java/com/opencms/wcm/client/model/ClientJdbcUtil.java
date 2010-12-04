package com.opencms.wcm.client.model;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-4
 * Time: 12:18:47
 * To change this template use File | Settings | File Templates.
 */
public class ClientJdbcUtil {

    public static final int INTNULLVALUE = -999999;
	public static final double DOUBLENULLVALUE = -999999.99;
	public static final float FLOATNULLVALUE = -999999.99f;

	public static boolean isBlankOrNull(String value){
		if(null==value||value.equals("")){
			return true;
		}
		return false;
	}

	public static boolean isNull(String value){
		if(null==value){
			return true;
		}
		return false;
	}

	public static boolean isNull(Object value){
		if(null==value){
			return true;
		}
		return false;
	}

	public static boolean isNull(int value){
		if(value== ClientJdbcUtil.INTNULLVALUE){
			return true;
		}
		return false;
	}

	public static boolean isNull(double value){
		if(value== ClientJdbcUtil.DOUBLENULLVALUE){
			return true;
		}
		return false;
	}

	public static boolean isNull(float value){
		if(value== ClientJdbcUtil.FLOATNULLVALUE){
			return true;
		}
		return false;
	}

	public static void main(String[] args){
		System.out.println(ClientJdbcUtil.isNull(""));
	}
}
