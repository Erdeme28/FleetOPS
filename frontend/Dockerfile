# Pasul 1: Alegem imaginea de bază
# nginx:alpine este o versiune foarte mică și rapidă de Linux cu Nginx preinstalat
FROM nginx:alpine

# Pasul 2: Copiem fișierele tale în container
# Punctul "." înseamnă "tot ce e în folderul frontend de pe PC-ul meu"
# Destinația "/usr/share/nginx/html" este locul standard de unde Nginx servește site-uri
COPY . /usr/share/nginx/html

# Pasul 3: Expunem portul (informativ)
EXPOSE 80