package ch.silviowangler.map4j.mapstruct;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by Silvio Wangler on 16/04/16.
 */
public final class MapperRegistry
{

   private Map<CombinedClassKey, Callable> mappers = new HashMap<> ();

   public void addMapping (Class aClass, Class bClass, Callable callable)
   {
      mappers.put (new CombinedClassKey (aClass, bClass), callable);
   }

   public <T> Callable<MapperWrapper> resolveConsumer (Class<?> sourceClass, Class<T> targetClass)
   {

      CombinedClassKey key = new CombinedClassKey (sourceClass, targetClass);
      if (mappers.containsKey (key))
      {
         Callable callable = mappers.get (key);
         return callable;
      }
      else
      {
         throw new NoSuchMappingException ("No such mapping for source class " + key.getA ().getSimpleName () + " and target class " + key.getB ().getSimpleName (), key.getA (), key.getB ());
      }
   }
}
