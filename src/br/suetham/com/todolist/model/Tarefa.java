package br.suetham.com.todolist.model;

import java.time.LocalDate;

public class Tarefa {
	private long id;
	private LocalDate dataCriacao;
	private LocalDate dataLimite;
	private LocalDate dataFinalizada;
	private String tarefaNome;
	private String comentario;
	private StatusTarefa status;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public LocalDate getDataLimite() {
		return dataLimite;
	}
	public void setDataLimite(LocalDate dataLimite) {
		this.dataLimite = dataLimite;
	}
	public LocalDate getDataFinalizada() {
		return dataFinalizada;
	}
	public void setDataFinalizada(LocalDate dataFinalizada) {
		this.dataFinalizada = dataFinalizada;
	}
	public String getTarefaNome() {
		return tarefaNome;
	}
	public void setTarefaNome(String tarefaNome) {
		this.tarefaNome = tarefaNome;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public StatusTarefa getStatus() {
		return status;
	}
	public void setStatus(StatusTarefa status) {
		this.status = status;
	}
	
	
}
