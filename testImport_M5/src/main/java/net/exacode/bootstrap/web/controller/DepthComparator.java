package net.exacode.bootstrap.web.controller;

import java.util.Comparator;

public class DepthComparator implements Comparator<String[]>{

	@Override
	public int compare(String[] arg0, String[] arg1) {
		// TODO Auto-generated method stub
		return Integer.parseInt(arg1[4]) - Integer.parseInt(arg0[4]);
	}

}
