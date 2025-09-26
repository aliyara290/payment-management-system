# Payment Management System - Development Commands

.PHONY: help start stop restart clean logs mysql phpmyadmin status

help: ## Show this help message
	@echo "Payment Management System - Docker Commands"
	@echo "==========================================="
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-15s\033[0m %s\n", $$1, $$2}'

start: ## Start all services
	@echo "🚀 Starting Payment Management System..."
	docker-compose up -d
	@echo "✅ Services started!"
	@echo "📊 phpMyAdmin: http://localhost:8080"
	@echo "🗄️  MySQL: localhost:3306"

stop: ## Stop all services
	@echo "🛑 Stopping services..."
	docker-compose down
	@echo "✅ Services stopped!"

restart: stop start ## Restart all services

clean: ## Remove containers, volumes, and networks
	@echo "🧹 Cleaning up everything..."
	docker-compose down -v --remove-orphans
	docker system prune -f
	@echo "✅ Cleanup complete!"

logs: ## Show logs for all services
	docker-compose logs -f

mysql: ## Connect to MySQL CLI
	@echo "🗄️  Connecting to MySQL..."
	@echo "Password: rootpassword"
	docker exec -it payment_mysql mysql -u root -p

phpmyadmin: ## Open phpMyAdmin in browser
	@echo "🌐 Opening phpMyAdmin..."
	open http://localhost:8080

status: ## Show status of all services
	@echo "📊 Service Status:"
	docker-compose ps
	@echo ""
	@echo "📈 Resource Usage:"
	docker stats --no-stream