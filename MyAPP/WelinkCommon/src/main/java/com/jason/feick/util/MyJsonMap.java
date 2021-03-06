package com.jason.feick.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class MyJsonMap extends HashMap<String, Object>
  implements  Cloneable, Serializable
{
  private static final long serialVersionUID = 1L;

  public String getString(Object key)
  {
    String s = "";
    try {
      s = get(key).toString().trim();
    } catch (Exception e) {
      s = "";
    }
    return s;
  }

  public String getString(Object key, String defauleValue)
  {
    String s = "";
    try {
      s = get(key).toString().trim();
    } catch (Exception e) {
      s = defauleValue;
    }
    return s;
  }

  public String getStringNoNull(String key)
  {
    return getStringNoNull(key, "");
  }

  public String getStringNoNull(String key, String defaultValue)
  {
    key = getString(key, defaultValue);
    return "null".equals(key) ? "" : key;
  }

  public String getStr(Object key)
  {
    String s = "";
    try {
      s = get(key).toString();
    } catch (Exception e) {
      s = "";
    }
    return s;
  }

  public int getInt(Object key)
  {
    return getInt(key, 0);
  }

  public int getInt(Object key, int defauleValue)
  {
    int i = 0;
    try {
      i = Integer.parseInt(getString(key));
    } catch (Exception e) {
      i = defauleValue;
    }
    return i;
  }

  public float getFloat(Object key)
  {
    return getFloat(key, 0.0F);
  }

  public float getFloat(Object key, float defauleValue)
  {
    float f = 0.0F;
    try {
      f = Float.parseFloat(getString(key));
    } catch (Exception e) {
      f = defauleValue;
    }
    return f;
  }
  
  public Long getLong(Object key, Long defauleValue)
  {
	  Long f = new Long(18516171091L);
    try {
      f = Long.parseLong(getString(key));
    } catch (Exception e) {
      f = defauleValue;
    }
    return f;
  }
  
  public Long getLong(Object key)
  {
    return getLong(key, 0L);
  }


  public double getDouble(Object key)
  {
    return getDouble(key, 0.0D);
  }

  public double getDouble(Object key, double defauleValue)
  {
    double d = 0.0D;
    try {
      d = Double.parseDouble(getString(key));
    } catch (Exception e) {
      d = defauleValue;
    }
    return d;
  }

  public boolean getBoolean(Object key)
  {
    return getBoolean(key, false);
  }

  public boolean getBoolean(Object key, boolean defauleValue)
  {
    boolean b = false;
    try {
      b = Boolean.parseBoolean(getString(key));
    } catch (Exception e) {
      b = defauleValue;
    }
    return b;
  }

  public MyJsonMap getJsonMap(Object key)
  {
    MyJsonMap data =
      JsonParseHelper.getJsonMap(getString(key));
    return data;
  }

  public List<MyJsonMap> getList_JsonMap(Object key)
  {
    return JsonParseHelper.getList_JsonMap(getString(key));
  }

  public String toJson()
  {
    if (keySet().size() == 0) {
      return "{}";
    }
    return new JsonMapOrListJsonMap2JsonUtil().map2Json(this);
  }

  public Object clone()
  {
    return super.clone();
  }
}