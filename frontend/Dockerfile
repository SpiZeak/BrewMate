FROM oven/bun:alpine

WORKDIR /app

RUN apk add --no-cache libc6-compat make g++ python3

CMD ["sh", "-c", "bun install && bun run dev"]
