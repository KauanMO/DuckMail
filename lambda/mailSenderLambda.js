const nodemailer = require('nodemailer');
require('dotenv').config();

const generateTransporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: process.env.EMAIL_USER,
        pass: process.env.EMAIL_PASS
    }
});

exports.handler = async event => {
    // const { to, subject, body } = JSON.parse(event);

    const mailOptions = {
        from: "duckmail.mailer.com",
        to: event.to,
        subject: event.subject,
        body: event.body
    }

    try {
        const info = await generateTransporter.sendMail(mailOptions);

        return {
            statusCode: 200,
            body: JSON.stringify({
                info
            })
        };
    } catch (e) {
        return {
            statusCode: 500,
            body: JSON.stringify({
                message: event
            })
        };
    }
}