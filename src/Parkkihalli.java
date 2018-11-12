import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

class Parkkihalli {

	private static List<TuloJaLahtoAika> tuloJaLahtoAjat = new ArrayList<>();
	private static final double MINUUTTIHINTA = 0.05;
	private static TuloJaLahtoAika parkkihalliPaikat[] = new TuloJaLahtoAika[162]; // 3 x 6 x 9 = 162
	private static double parkkihallinKassa = 0;
	private static final Date PAATTYMISAIKA = new Date(Timestamp.valueOf("2018-09-15 09:00:00").getTime());

	private static List<TuloJaLahtoAika> kaytetytParkkiajat = new ArrayList<>();
	private static List<TuloJaLahtoAika> ylikysyntaParkkiajat = new ArrayList<>();

	private static long kaytetytMinuutit;

	public static void main(String[] args) {

		Parkkihalli parkkihalli = new Parkkihalli();

		// aja testidata parkkihallin läpi määritettyyn päättymisaikaan asti
		for (TuloJaLahtoAika tuloJaLahtoAika : tuloJaLahtoAjat) {
			if (tuloJaLahtoAika.getArrivalTime().before(PAATTYMISAIKA)) {
				parkkihalli.uudenAutonVastaanotto(tuloJaLahtoAika);
			} else {
				break;
			}
		}

		// LOPETUS
		rahastaLoputAutot(); 

		System.out.println("");
		System.out.println("STATISTIIKKA " + PAATTYMISAIKA);
		System.out.println(
				"Autoja parkissa: " + Arrays.stream(parkkihalliPaikat).filter(x -> x != null).distinct().count());
		System.out.println("Kertyneet parkkimaksut: " + String.format("%.2f", parkkihallinKassa) + " €");
		tulostaParkkihallipaikat();
		tulostaKayttoasteViikkotasolla();
		tulostaKapasiteetinTakiaMeneteytTuototViikkotasolla();
	}

	public Parkkihalli() {
		for (int i = 0; i < 50000; i++) {
			tuloJaLahtoAjat.add(new TuloJaLahtoAika());
		}
		Collections.sort(tuloJaLahtoAjat);
	}

	public void uudenAutonVastaanotto(TuloJaLahtoAika tuloJaLahtoAika) {

		poistaLahteneetAutotParkkihallistaTuloaikaanMennessa(tuloJaLahtoAika);

		int vapaanRuudunIndeksi = etsiVapaaParkkiRuutu();
		if (vapaanRuudunIndeksi == -1) {
			ylikysyntaParkkiajat.add(tuloJaLahtoAika);
		} else {
			parkkihalliPaikat[vapaanRuudunIndeksi] = tuloJaLahtoAika;
			kaytetytParkkiajat.add(tuloJaLahtoAika);
		}

	}

	public int etsiVapaaParkkiRuutu() {
		for (int i = 0; i < parkkihalliPaikat.length; i++) {
			if (parkkihalliPaikat[i] == null) {
				return i;
			}
		}
		return -1;
	}

	public void poistaLahteneetAutotParkkihallistaTuloaikaanMennessa(TuloJaLahtoAika tuloJaLahtoAika) {

		for (int i = 0; i < parkkihalliPaikat.length; i++) {
			if (parkkihalliPaikat[i] != null
					&& parkkihalliPaikat[i].getDepartureTime().before(tuloJaLahtoAika.getArrivalTime())) {
				rahastaAutoKokoParkkiajalta(parkkihalliPaikat[i]);
				parkkihalliPaikat[i] = null;
			}
		}
	}

	public void rahastaAutoKokoParkkiajalta(TuloJaLahtoAika tuloJaLahtoAika) {
		long diffInMillies = Math
				.abs(tuloJaLahtoAika.getArrivalTime().getTime() - tuloJaLahtoAika.getDepartureTime().getTime());
		long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
		parkkihallinKassa += diff * MINUUTTIHINTA;
	}

	public static void rahastaLoputAutot() {
		for (TuloJaLahtoAika tuloJaLahtoAika : parkkihalliPaikat) {
			if (tuloJaLahtoAika != null) {
				rahastaAutoPaattymisaikaanAsti(tuloJaLahtoAika);
			}
		}
	}

	public static void rahastaAutoPaattymisaikaanAsti(TuloJaLahtoAika tuloJaLahtoAika) {
		long diffInMillies = Math.abs(tuloJaLahtoAika.getArrivalTime().getTime() - PAATTYMISAIKA.getTime());
		long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
		parkkihallinKassa += diff * MINUUTTIHINTA;
	}

	public static void tulostaParkkihallipaikat() {

		System.out.println(" ");
		System.out.println("Autojen järjestys parkkihallissa:");
		System.out.println("[X] = varattu");
		System.out.println("[ ] = vapaa");

		for (int i = 0; i < parkkihalliPaikat.length; i++) {

			if (i % 9 == 0) {
				System.out.println("");
			}

			switch (i) {
			case 0:
				System.out.println("1. kerros");
				break;
			case 54:
				System.out.println("\n2. kerros");
				break;
			case 108:
				System.out.println("\n3. kerros");
				break;
			}

			if (parkkihalliPaikat[i] == null) {
				System.out.print("[ ]");
			} else {
				System.out.print("[X]");
			}
		}
	}

	public static boolean isBetween(int x, int lower, int upper) {
		return lower <= x && x <= upper;
	}

	public List<TuloJaLahtoAika> getTimeStamps() {
		return tuloJaLahtoAjat;
	}

	public String toString() {
		return tuloJaLahtoAjat.toString();
	}

	public static void tulostaKayttoasteViikkotasolla() {

		long kokonaisenViikonMinuutitKapasiteetti = 7 * 24 * 60 * parkkihalliPaikat.length;
		Map<Integer, Long> viikkotasonKaytetytMinuutit = haeViikkotasonMinuutit(kaytetytParkkiajat);

		System.out.println("\n\nParkkihallin viikottainen käyttöaste:");
		viikkotasonKaytetytMinuutit.forEach((k, v) -> System.out.println("Viikko " + k + " käyttöaste: "
				+ String.format("%.2f", ((double) v / (double) kokonaisenViikonMinuutitKapasiteetti) * 100) + " %"));

	}

	public static void tulostaKapasiteetinTakiaMeneteytTuototViikkotasolla() {

		Map<Integer, Long> viikkotasonMinuutit = haeViikkotasonMinuutit(ylikysyntaParkkiajat);

		System.out.println("\nYlikysynnästä johtuvat menetetyt parkkimaksut viikkotasolla:");
		viikkotasonMinuutit.forEach((k, v) -> System.out.println("Viikko " + k + " menetetyt parkkimaksut: " + String.format("%.2f", ((double) v * MINUUTTIHINTA)) + " €"));

	}

	public static Map<Integer, Long> haeViikkotasonMinuutit(List<TuloJaLahtoAika> parkkiajat) {
		Collections.sort(parkkiajat);

		// aloitusviikko & lopetusviikko nro:t
		Calendar cal = Calendar.getInstance();
		cal.setTime(parkkiajat.get(0).getArrivalTime());
		int ensimmainenViikko = cal.get(Calendar.WEEK_OF_YEAR);
		cal.setTime(PAATTYMISAIKA);
		int viimeinenViikko = cal.get(Calendar.WEEK_OF_YEAR);

		Map<Integer, Long> viikkotasonMinuutit = new LinkedHashMap<Integer, Long>(); // <viikko nro, käytetyt minuutit>

		for (int i = ensimmainenViikko; i <= viimeinenViikko; i++) {
			viikkotasonMinuutit.put(i, (long) 0);

			// käy jokainen viikko läpi minuutti kerrallaan
			for (TuloJaLahtoAika tuloJaLahtoAika : kaytetytParkkiajat) {
				Calendar minuuttilaskuri = Calendar.getInstance();
				minuuttilaskuri.setTime(tuloJaLahtoAika.getArrivalTime());

				// lisää 1 minuutti viikon käytettyihin minuutteihin jos sisältyy viikkoon
				while (minuuttilaskuri.get(Calendar.WEEK_OF_YEAR) == i
						&& tuloJaLahtoAika.getDepartureTime().after(minuuttilaskuri.getTime())) {
					viikkotasonMinuutit.put(i, viikkotasonMinuutit.get(i) + (long) 1);
					minuuttilaskuri.add(Calendar.MINUTE, 1);
				}
			}
		}

		return viikkotasonMinuutit;
	}

	public class TuloJaLahtoAika implements Comparable<TuloJaLahtoAika> {
		private Date arrival;
		private Date departure;

		public TuloJaLahtoAika() {
			Long minArrivalTime = Timestamp.valueOf("2018-08-01 00:00:00").getTime();
			Long maxDepartureTime = Timestamp.valueOf("2018-09-30 00:00:00").getTime();
			Long randomTimeBetweenMaxAndMin = minArrivalTime
					+ (long) (Math.random() * (maxDepartureTime - minArrivalTime + 1));

			arrival = new Date(randomTimeBetweenMaxAndMin);
			departure = new Date(maxDepartureTime);

			while ((departure).after(new Date(maxDepartureTime - 1))) {
				departure = new Date((long) (randomTimeBetweenMaxAndMin + new Random().nextGaussian() * (120 * 60000)
						+ 360 * 60000));
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			dateFormat.format(arrival);
			dateFormat.format(departure);
		}

		private Date getArrivalTime() {
			return arrival;
		}

		private Date getDepartureTime() {
			return departure;
		}

		public String toString() {
			return "saapumisaika: " + arrival + " lähtöaika: " + departure + System.getProperty("line.separator");
		}

		public int compareTo(TuloJaLahtoAika tuloJaLahtoAika) {
			return this.getArrivalTime().compareTo(tuloJaLahtoAika.getArrivalTime());
		}

	}

}
