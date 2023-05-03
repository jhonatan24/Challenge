package main

import (
	"log"
	"math/rand"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
)

type Response struct {
	Date       time.Time `json:"createDate"`
	Percentage int       `json:"percentage"`
}

func main() {
	r := gin.Default()
	r.GET("/generate", generateResul)
	r.Run()
}

func generateResul(c *gin.Context) {
	log.Println("Received a request ")
	rand.Seed(time.Now().UnixNano())
	number := rand.Intn(100)
	resul := Response{time.Now(), number}
	log.Printf("time: %s, number:%d ", time.Now(), number)
	c.IndentedJSON(http.StatusOK, resul)
}
