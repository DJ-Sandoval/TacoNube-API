#!/bin/bash

# ==========================================
# TacoNube API - Git Deploy Script
# Autor: Jose Armando Sandoval Santana
# ==========================================

set -e

BRANCH="dev"

# Si el usuario escribe un mensaje, usarlo.
# Si no, generar uno automáticamente.
if [ -z "$1" ]; then
    COMMIT_MESSAGE="Actualización $(date '+%Y-%m-%d %H:%M:%S')"
else
    COMMIT_MESSAGE="$*"
fi

echo "📂 Agregando archivos..."
git add .

echo "📝 Creando commit..."
git commit -m "$COMMIT_MESSAGE" || {
    echo "⚠️ No hay cambios para hacer commit."
    exit 0
}

echo "🚀 Subiendo cambios a $BRANCH..."
git push origin "$BRANCH"

echo ""
echo "✅ Proyecto actualizado correctamente."
echo "🌐 https://github.com/DJ-Sandoval/TacoNube-API"