
export const errorAlert = (error) => {
    const message = error.response.data.result === false ?
        error.response.data.message : error.message
    
    alert(message);
}