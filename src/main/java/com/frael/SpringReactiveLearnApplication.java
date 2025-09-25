package com.frael;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class SpringReactiveLearnApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveLearnApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//monoVsFlux();
	}

	public void monoVsFlux(){
		/// USO DE REFERENCIA DE METODOS
		Stream.of("a", "b", "c")
				.map(StringBuilder::new)
				.forEach(System.out::println);

		System.out.println("\n");

		// Emite
		Flux<String> listaNombres = Flux.just("Carlos", "Fabian", "Hernan").doOnNext(System.out::println); // doOnNext(n
																											// ->
																											// System.out.println(n));
		Flux<String> listaApellidos = Flux.just("Marial", "Fernandez", "").doOnNext(n -> {
			if (n.isEmpty()) {
				throw new RuntimeException("Esta vacioo !!");
			}
			System.out.println(n);
		}); // doOnNext(n -> System.out.println(n));

		// Principio de inmutabilidad
		// listaNombres.doOnNext(nombre -> System.out.println(nombre)); no refleja
		// porque esto es otro flujo

		// Escucha/Suscribe
		// listaNombres.subscribe();
		// listaNombres.subscribe(System.out::println);
		listaApellidos.subscribe(System.out::println, e -> System.out.println(e));

		// Ver cuando se completa
		listaNombres.subscribe(System.out::println, e -> System.out.println(e),
				new Runnable() {

					// Se impreme cuando se termina completamente el flujo
					@Override
					public void run() {
						System.out.println("Termino correctamente el flujo");

					}

				});

		// Salto de linea
		System.out.println("\n");

		Flux<String> listaAnimales = Flux.just("Perro", "Gato", "Loro").doOnNext(n -> {
			if (n.isEmpty()) {
				throw new RuntimeException("Esta vacioo !!");
			}
			System.out.println(n);
		}).map(a -> {return a.toUpperCase();});
		// Se imprime en mayuscula al terminar
		listaAnimales.subscribe(System.out::println);

		// Que es el FlatMap
		// el resultado de flatMap sobre un Flux es otro Flux<String>, no un Mono.
		List<String> verduras = List.of("Papas", "Tomates", "Cebollas");

		Flux.fromIterable(
				verduras).flatMap(v -> {
					if (v.equals("Papas")) {
						return Mono.just(v);
					} else {
						return Mono.empty();
					}
				});

		System.out.println("\n");

		/// UNIR FLUJOS MONO
		/// Uso de fromCallable: Operaciones costosas, consultas a BD, llamadas externas
		/// Define dos monos que simulan datos independientes.
		/// Los combina con flatMap y map.
		/// Al suscribirse, imprime la concatenación de ambos resultados.
		Mono<String> userMono = Mono.fromCallable(() -> new String("Esto es un usuario"));
		Mono<String> publicacionMono = Mono.fromCallable(() -> new String("Esto es una publicacion"));

		userMono.flatMap(user -> publicacionMono.map(p -> new String(user + " " + p))).subscribe(System.out::println);
		
		/// zipWith espera a que ambos monos emitan un valor.
		/// Luego aplica la función (user, p) para combinarlos
		/// El resultado es otro Mono con la concatenación.
		userMono.zipWith(publicacionMono).map(t -> {
			String user = t.getT1();
			String publicacion = t.getT2();

			return new String("Nuevo: " + user + " Nuevo " + publicacion);
		}).subscribe(System.out::println);
	}

}
