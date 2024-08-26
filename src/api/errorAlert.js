
export const errorAlert = (error) => {
    const message = error.response.data.message;
    alert(message);
}