package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalculaDiferencaAno
{
  public static int calculaDiferenca(Date dataInicial, Date dataFinal)
  {
/* 23 */     Date inici = dataInicial;
/* 24 */     SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss,SSS");

/* 26 */     Date atual = dataFinal;
/* 27 */     SimpleDateFormat formatere = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss,SSS");

/* 29 */     Calendar cal = Calendar.getInstance();
/* 30 */     Calendar cal2 = Calendar.getInstance();
/* 31 */     cal.setTime(atual);
/* 32 */     cal2.setTime(inici);
/* 33 */     long m1 = cal.getTimeInMillis();
/* 34 */     long m2 = cal2.getTimeInMillis();
/* 35 */     int dii = (int)((m1 - m2) / 86400000L);

/* 37 */     int di = (int)(m1 - m2);

/* 42 */     return dii;
  }
}

/* Location:           /home/frank/Desktop/sistemaccblista/util/
 * Qualified Name:     br.com.sistemaccblista.util.calculaDiferencaAno
 * JD-Core Version:    0.6.2
 */