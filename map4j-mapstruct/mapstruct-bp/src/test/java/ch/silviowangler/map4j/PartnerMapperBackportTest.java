package ch.silviowangler.map4j;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import ch.silviowangler.map4j.mappers.PartnerMapper;

/**
 * Created by Silvio Wangler on 15/04/16.
 */
public class PartnerMapperBackportTest
{

   @Test
   public void mapPartnerDtoToPartnerVO ()
   {

      MapperRegistry registry = new MapperRegistry ();

      registry.addMapping (PartnerVO.class, PartnerDTO.class, new MapperWrapper ()
      {

         @Override
         public Object call () throws Exception
         {
            PartnerMapper mapper = PartnerMapper.INSTANCE;
            if (this.getTarget () == null)
            {
               return mapper.partnerVoToPartnerDto ((PartnerVO) getSource ());
            }
            mapper.updatePartnerDTOfromPartnerVO ((PartnerVO) getSource (), (PartnerDTO) getTarget ());
            return getTarget ();
         }
      });

      MapStructMapper mapper = new MapStructMapper (registry);

      PartnerVO partnerVO = new PartnerVO ("Silvio", "Wangler", new Date (), "+41 44 444 44 44");

      PartnerDTO partnerDTO = mapper.map (partnerVO, PartnerDTO.class);

      assertEquals (partnerDTO.getVorname (), partnerVO.getFirstname ());
      assertEquals (partnerDTO.getNachname (), partnerVO.getLastname ());
      assertEquals (partnerDTO.getTelephone (), partnerVO.getTelephone ());
      assertEquals (partnerDTO.getGeburtstag (), partnerVO.getBirthdate ());

      PartnerDTO partnerDTO1 = new PartnerDTO ();

      mapper.map (partnerVO, partnerDTO1);

      assertEquals (partnerDTO1.getVorname (), partnerVO.getFirstname ());
      assertEquals (partnerDTO1.getNachname (), partnerVO.getLastname ());
      assertEquals (partnerDTO1.getTelephone (), partnerVO.getTelephone ());
      assertEquals (partnerDTO1.getGeburtstag (), partnerVO.getBirthdate ());
   }
}