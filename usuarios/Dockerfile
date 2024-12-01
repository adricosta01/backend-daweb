FROM mcr.microsoft.com/dotnet/sdk:6.0
WORKDIR /app

COPY . .
RUN dotnet build

# Establece el puerto del servidor web
ENV ASPNETCORE_KESTREL__ENDPOINTS__HTTP__URL=http://+:7065

CMD ["dotnet", "run"]
EXPOSE 7065