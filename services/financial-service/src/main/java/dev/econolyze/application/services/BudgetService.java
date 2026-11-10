package dev.econolyze.application.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class BudgetService {

	private static final Logger log = LoggerFactory.getLogger(BudgetService.class);

	public BudgetService() {
	}
}
