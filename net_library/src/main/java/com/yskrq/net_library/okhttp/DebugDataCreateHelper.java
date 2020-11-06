package com.yskrq.net_library.okhttp;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调试数据一键生成辅助类
 * 实现该方法 调用下方法
 * <p>
 * Bean debug = (Bean) new Bean().getCreateData();
 */

public class DebugDataCreateHelper {

    public static DebugDataCreateHelper mTool;
    private String[] strings;
//        private HashMap<String, List<Integer>> ints = new HashMap<>();

    public static DebugDataCreateHelper instans() {
        if (mTool == null) {
            mTool = new DebugDataCreateHelper();
        }
        return mTool;
    }

    private DebugDataCreateHelper() {
    }

    public Object createDebugData(Class object, int index) {
        Object obj = createData2(index, object);
        return obj;
    }

    public Object createData2(int index, Class clazz) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (isNormalData(field.getType())) {
                    Object val = getNormalData(index, field.getType(), field.getName());
                    field.set(obj, val);
                } else if (List.class.isAssignableFrom(field.getType())) {
                    Type t = getFieldType(clazz, field);
                    if (t != null) {
                        List data = new ArrayList();
                        // getActualTypeArguments 获取泛型的类型 List<T> 中 T 的类型
                        Type innerType = ((ParameterizedType) t).getActualTypeArguments()[0];
                        if (isNormalData(innerType.getClass().getName())) {
                            LOG.e("DebugDataCreateHelper", "createData2.58:");
                            data = new ArrayList();
                            for (int i = 0; i < 5; i++) {
                                data.add(getNormalData(i, Class
                                        .forName(innerType.getClass().getName()), field.getName()));
                            }
                        } else {
                            LOG.e("DebugDataCreateHelper", "createData2.66:" + innerType);
                            Class cla = Class.forName(innerType.toString().replace("class ", ""));
                            for (int i = 0; i < 3; i++) {
                                data.add(createDebugData(cla, i));
                            }
                        }
                        field.set(obj, data);
                    }
                } else if (Map.class.isAssignableFrom(field.getType())) {
                    Type t = getFieldType(clazz, field);
                    if (t != null) {
                        Map data = new HashMap();
                        Type innerType = ((ParameterizedType) t).getActualTypeArguments()[0];
                        Type innerType2 = ((ParameterizedType) t).getActualTypeArguments()[1];
                        for (int i = 0; i < 5; i++) {
                            Object key = null;
                            Object value = null;
                            if (isNormalData(innerType.getClass())) {
                                key = getNormalData(i, innerType.getClass().getName());
                            } else {
                                Class cla = Class.forName(innerType.toString().replace("class ", ""));
                                key = createDebugData(cla, index * 100 + i);
                            }
                            if (isNormalData(innerType2.getClass())) {
                                value = getNormalData(i, innerType.getClass().getName());
                            } else {
                                Class cla = Class.forName(innerType2.toString().replace("class ", ""));
                                value = createDebugData(cla, index * 100 + i);
                            }
                            if (key != null && value != null) {
                                data.put(key, value);
                            } else {
                                System.out.println("DebugDataCreateHelper --> Map empty");
                            }
                        }
                        field.set(obj, data);
                    }
                } else if (checkAndSetNormalData(index, obj, field)) {
                    System.out.println("DebugData_Helper --> 数组");
                } else {//是一个复合数据
                    Class cla = Class.forName(field.getType().getName());
                    Object o = createData2(index, cla);
                    System.out.println("复合数据 " + field.getType().getName());
                    if (o == null) {
                        System.out.println("复合数据  --> 创建失败");
                    } else {
                        System.out.println("复合数据  --> 0 = " + o);
                        field.set(obj, o);
                    }
                }
            }


        } catch (IllegalAccessException e) {
            System.out.println("DebugData_Helper.160  --> error:" + e.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.out.println("DebugData_Helper.163  --> error:" + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private Object getNormalData(int index, String paramName) {
        if (Integer.class.getName().equals(paramName) || int.class.getName().equals(paramName)) {
            return getInteger(index);
        } else if (String.class.getName().equals(paramName)) {
            return getString(index, paramName);
        } else if (Long.class.getName().equals(paramName) || long.class.getName().equals(paramName)) {
            return getLong(index);
        } else if (Double.class.getName().equals(paramName) || double.class.getName().equals(paramName)) {
            return getDouble(index);
        } else if (Float.class.getName().equals(paramName) || float.class.getName().equals(paramName)) {
            return getFloat(index);
        } else if (Boolean.class.getName().equals(paramName) || boolean.class.getName().equals(paramName)) {
            return getBoolean(index);
        }
        return null;
    }

    private Object getNormalData(int index, Class<?> clazz, String paramName) {
        if (Integer.class.isAssignableFrom(clazz) || int.class.isAssignableFrom(clazz)) {
            return getInteger(index);
        } else if (String.class.isAssignableFrom(clazz)) {
            return getString(index, paramName);
        } else if (Long.class.isAssignableFrom(clazz) || long.class.isAssignableFrom(clazz)) {
            return getLong(index);
        } else if (Double.class.isAssignableFrom(clazz) || double.class.isAssignableFrom(clazz)) {
            return getDouble(index);
        } else if (Float.class.isAssignableFrom(clazz) || float.class.isAssignableFrom(clazz)) {
            return getFloat(index);
        } else if (Boolean.class.isAssignableFrom(clazz) || boolean.class.isAssignableFrom(clazz)) {
            return getBoolean(index);
        }
        return null;
    }

    private boolean isNormalData(Class<?> clazz) {//常规数据
        return Integer.class.isAssignableFrom(clazz)
                || int.class.isAssignableFrom(clazz)
                || String.class.isAssignableFrom(clazz)
                || Long.class.isAssignableFrom(clazz)
                || long.class.isAssignableFrom(clazz)
                || Double.class.isAssignableFrom(clazz)
                || double.class.isAssignableFrom(clazz)
                || Float.class.isAssignableFrom(clazz)
                || float.class.isAssignableFrom(clazz)
                ;
    }

    private boolean isNormalData(String clazzName) {//常规数据
        return Integer.class.getName().equals(clazzName)
                || int.class.getName().equals(clazzName)
                || String.class.getName().equals(clazzName)
                || Long.class.getName().equals(clazzName)
                || long.class.getName().equals(clazzName)
                || Double.class.getName().equals(clazzName)
                || double.class.getName().equals(clazzName)
                || Float.class.getName().equals(clazzName)
                || float.class.getName().equals(clazzName)
                ;
    }

    //无视访问权限 private
    private Type getFieldType(Class clazz, Field field) {
        Type t;
        String param = field.getName();
        System.out.println("DebugData_Helper.LINE  --> 349 param = " + param);
        try {
            t = clazz.getDeclaredField(param.substring(0, 1).toLowerCase() + param.substring(1)).getGenericType();
        } catch (NoSuchFieldException e) {
            try {
                t = clazz.getDeclaredField(param.substring(0, 1).toUpperCase() + param.substring(1)).getGenericType();
            } catch (NoSuchFieldException e1) {
                return null;
            }
        }
        if (t != null && ParameterizedType.class.isAssignableFrom(t.getClass())) {
            return t;
        }
        return null;
    }


    private boolean checkAndSetNormalData(int index, Object obj, Field field) {
        String methodParam = field.getType().getSimpleName();
        try {
//            if (isNormalData(methodParam)) {
//                method.invoke(obj, getNormalData(index, methodParam, param));
//            } else
            if ("int[]".equals(methodParam)) {
                makeArray(obj, 10, int.class, field);
            } else if ("long[]".equals(methodParam)) {
                makeArray(obj, 10, double.class, field);
            } else if ("double[]".equals(methodParam)) {
                makeArray(obj, 10, double.class, field);
            } else if ("float[]".equals(methodParam)) {
                makeArray(obj, 10, float.class, field);
            } else if ("int[][][]".equals(methodParam)) {
                int[] ac = new int[]{3, 5, 10};
                Object array = Array.newInstance(Integer.TYPE, ac);
                for (int hang = 0; hang < ac[0]; hang++) {
                    // 取出三维数组的第3行，为一个数组
                    for (int lie = 0; lie < ac[1]; lie++) {
                        Object arrayObj = Array.get(array, hang);
                        arrayObj = Array.get(arrayObj, lie);
                        for (int shu = 0; shu < ac[2]; shu++) {
                            Array.setInt(arrayObj, shu, hang * 5 + shu * 13 + lie * 11);
                        }
                    }
                }
                // 动态数组和普通数组的转换：强行转换成对等的数组
//                            int arrayCast[][][] = (int[][][]) array;
                field.setAccessible(true);
                field.set(obj, array);


            } else if ("java.lang.String[]".equals(methodParam)) {
                int lenth = 10;
                Class<?> classType = Class.forName("java.lang.String");
                Object array = Array.newInstance(classType, lenth);
                for (int i = 0; i < lenth; i++) {
                    Array.set(array, i, getString(index + i, field.getName()));
                }
                field.setAccessible(true);
                field.set(obj, array);
            } else {
                return false;
            }
        } catch (IllegalAccessException e) {
            System.out.println("DebugData_Helper.406  --> error:" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("DebugData_Helper.408  --> error:" + e.getMessage());
        }
        return true;
    }

    //给 object 设置数组数据
    private <T> void makeArray(Object object, int length, Class<T> clazz, Field field) throws IllegalAccessException {
        // 创建一个长度为10的字符串数组
        Object array = Array.newInstance(clazz, length);
        // 把索引位置为5的元素设为"hello"
        for (int i = 0; i < length; i++) {
            Array.set(array, i, getArrayNumber(i));
        }
        // 获得索引位置为5的元素的值
        field.setAccessible(true);
        field.set(object, array);
    }

    private boolean getBoolean(int index) {
//            List<Integer> data = ints.get("boolean");
//            if (data != null) {
//                return data.get(index) == 1;
//            }
        return index % 2 == 0;
    }

    private int getInteger(int index) {
//            List<Integer> data = ints.get("int");
//            if (data != null) {
//                return index;
//            }
        return index;
    }

    private long getLong(int index) {
//            List<Integer> data = ints.get("long");
//            if (data != null) {
//                return data.get(index);
//            }
        return 100000L + index;
    }

    private double getDouble(int index) {
//            List<Integer> data = ints.get("double");
//            if (data != null) {
//                return data.get(index);
//            }
        return 10.0D + index;
    }

    private float getFloat(int index) {
//            List<Integer> data = ints.get("float");
//            if (data != null) {
//                return data.get(index);
//            }
        return 100.00F + index;
    }

    private int getArrayNumber(int index) {
        return 10 * index;
    }

    private String getString(int index, String param) {
        if (strings != null) {
            return strings[index];
        }
        return param + index;
    }

//        private  Object getNormalData(int index, String methodParam, String param) {
//            if ("java.lang.String".equals(methodParam)) {
//                return getString(index, param);
//            } else if ("boolean".equals(methodParam)) {
//                return getBoolean(index);
//            } else if ("int".equals(methodParam)) {
//                return getInteger(index);
//            } else if ("long".equals(methodParam)) {
//                return getLong(index);
//            } else if ("double".equals(methodParam)) {
//                return getDouble(index);
//            } else if ("float".equals(methodParam)) {
//                return getFloat(index);
//            }
//            return null;
//        }
}