package domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetResponse {
	
	private List<Result> results = null;

	public PlanetResponse() {
	}

	public PlanetResponse(List<Result> results) {
		this.results = results;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public PlanetResponse withResults(List<Result> results) {
		this.results = results;
		return this;
	}

}