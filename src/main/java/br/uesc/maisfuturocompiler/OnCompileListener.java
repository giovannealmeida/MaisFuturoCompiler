/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uesc.maisfuturocompiler;

/**
 *
 * @author gamessias
 */
public interface OnCompileListener {
    public static final int COMPILE_SUCCESS = 0;
    public static final int COMPILE_ERROR = 1;
    
    public void onFinished(int status);
    public void onAborted(String title, String message);
}
