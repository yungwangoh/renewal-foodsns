import axios from "axios";

export const memberApi = {

    create: async (email: string, nickName: string, password: string, profileImage: File) => {
        const formData = new FormData();
        formData.append('email', email);
        formData.append('nickName', nickName);
        formData.append('password', password);
        if (profileImage) {
            formData.append('profileImage', profileImage);
        }

        const config = {
            headers: {
                'Content-Type': 'multipart/form-data',
            }
        }

        const response = await axios.post('/api/sign-up', formData, config);

        return response.data;
    }
}