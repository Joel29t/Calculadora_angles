class Angle {

	private int grados;
	private int minutos;
	private int segundos;

	public Angle() {
		this(0, 0, 0);
	}

	public Angle(int grados, int minutos, int segundos) {
		setAngle(grados, minutos, segundos);
	}

	public Angle(String formatoSexagesimal) {
		String[] partes = formatoSexagesimal.split(":");

			if (partes.length > 3) {
	            throw new IllegalArgumentException("Formato inválido. Debe ser GG:MM:SS");
	        }
			
			int grados = partes.length > 0 ? Integer.parseInt(partes[0]) : 0;
			int minutos = partes.length > 1 ? Integer.parseInt(partes[1]) : 0;
			int segundos = partes.length > 2 ? Integer.parseInt(partes[2]) : 0;

			if (partes.length == 2) {
				segundos = minutos;
				minutos = grados;
				grados = 0;
			} else if (partes.length == 1) {
				segundos = grados;
				minutos = 0;
				grados = 0;
			}

			setAngle(grados, minutos, segundos);
	
	}

	private void setAngle(int grados, int minutos, int segundos) {
	
		this.grados = grados;
		this.minutos = minutos;
		this.segundos = segundos;
	}

	public void sumar(Angle otroAngulo) {
		this.grados += otroAngulo.grados;
		this.minutos += otroAngulo.minutos;
		this.segundos += otroAngulo.segundos;
		normalizar();
	}

	public void restar(Angle otroAngulo) {
		this.grados -= otroAngulo.grados;
		this.minutos -= otroAngulo.minutos;
		this.segundos -= otroAngulo.segundos;
		normalizar();
	}

	public void normalizar() {
	    int totalSegundos = this.grados * 3600 + this.minutos * 60 + this.segundos;

	    totalSegundos = (totalSegundos % 1296000 + 1296000) % 1296000;

	    this.grados = totalSegundos / 3600;
	    this.minutos = (totalSegundos / 60) % 60;
	    this.segundos = totalSegundos % 60;
	}



	@Override
	public String toString() {
		return String.format("%02d:%02d:%02d", grados, minutos, segundos);
	}
}