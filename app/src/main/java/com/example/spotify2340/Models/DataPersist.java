package com.example.spotify2340.Models;

import android.provider.ContactsContract;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public interface DataPersist<T> {

    public void store(StringBuilder sb);

    public T load(BufferedReader br);

    default <U extends DataPersist> void storeType(U var, StringBuilder sb) {
        var.store(sb);
    }

    default <U extends DataPersist> U loadType(BufferedReader br, U var) {
        return (U) var.load(br);
    }

    default void storeType(boolean var, StringBuilder sb) {
        sb.append(var ? "true" : "false").append("\n");
    }

    default boolean loadBoolean(BufferedReader br) throws IOException {
        return br.readLine().equals("true");
    }

    default void storeType(byte var, StringBuilder sb) {
        sb.append(var).append("\n");
    }

    default byte loadByte(BufferedReader br) throws IOException {
        return Byte.parseByte(br.readLine());
    }

    default void storeType(char var, StringBuilder sb) {
        sb.append(var).append("\n");
    }

    default char loadChar(BufferedReader br) throws IOException{
        return br.readLine().charAt(0);
    }

    default void storeType(short var, StringBuilder sb) {
        sb.append(var).append("\n");
    }

    default short loadShort(BufferedReader br) throws IOException {
        return Short.parseShort(br.readLine());
    }

    default void storeType(int var, StringBuilder sb) {
        sb.append(var).append("\n");
    }

    default int loadInt(BufferedReader br) throws IOException {
        return Integer.parseInt(br.readLine());
    }

    default void storeType(long var, StringBuilder sb) {
        sb.append(var).append("\n");
    }

    default long loadLong(BufferedReader br) throws IOException {
        return Long.parseLong(br.readLine());
    }

    default void storeType(float var, StringBuilder sb) {
        sb.append(var).append("\n");
    }

    default float loadFloat(BufferedReader br) {
        return Float.parseFloat("\n");
    }

    default void storeType(double var, StringBuilder sb) {
        sb.append(var).append("\n");
    }

    default double loadDouble(BufferedReader br) throws IOException {
        return Double.parseDouble(br.readLine());
    }

    default void storeType(String var, StringBuilder sb) {
        sb.append(var).append("\n");
    }

    default String loadString(BufferedReader br) throws IOException {
        return br.readLine();
    }

}
