<template>
    <div class="col-md-12">
        <div class="card card-container">
            <img
                    class="profile-img-card"
                    id="profile-img"
                    src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
            />
            <form @submit.prevent="handleLogin" name="form">
                <div class="form-group">
                    <label for="username">Логин</label>
                    <input
                            class="form-control"
                            name="username"
                            type="text"
                            v-model="user.username"
                            v-validate="'required'"
                    />
                    <div
                            class="alert alert-danger"
                            role="alert"
                            v-if="errors.has('username')"
                    >Введите логин
                    </div>
                </div>
                <div class="form-group">
                    <label for="password">Пароль</label>
                    <input
                            class="form-control"
                            name="password"
                            type="password"
                            v-model="user.password"
                            v-validate="'required'"
                    />
                    <div
                            class="alert alert-danger"
                            role="alert"
                            v-if="errors.has('password')"
                    >Введите пароль
                    </div>
                </div>
                <div class="form-group">
                    <button :disabled="loading" class="btn btn-primary btn-block">
                        <span class="spinner-border spinner-border-sm" v-show="loading"></span>
                        <span>Вход</span>
                    </button>
                </div>
                <div class="form-group">
                    <div class="alert alert-danger" role="alert" v-if="message">{{message}}</div>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
    import User from '../../models/User';

    export default {
        name: 'Login',
        data() {
            return {
                user: new User('', ''),
                loading: false,
                message: ''
            };
        },
        computed: {
            loggedIn() {
                return this.$store.state.auth.status.loggedIn;
            }
        },
        created() {
            if (this.loggedIn) {
                this.$router.push('/profile');
            }
        },
        methods: {
            handleLogin() {
                this.loading = true;
                this.$validator.validateAll().then(isValid => {
                    if (!isValid) {
                        this.loading = false;
                        return;
                    }

                    if (this.user.username && this.user.password) {
                        this.$store.dispatch('auth/login', this.user).then(
                            () => {
                                this.$router.push('/profile');
                            },
                            error => {
                                this.loading = false;
                                this.message =
                                    (error.response && error.response.data) ||
                                    error.message ||
                                    error.toString();
                            }
                        );
                    }
                });
            }
        }
    };
</script>

<style scoped>
    label {
        display: block;
        margin-top: 10px;
    }

    .card-container.card {
        max-width: 350px !important;
        padding: 40px 40px;
    }

    .card {
        background-color: #f7f7f7;
        padding: 20px 25px 30px;
        margin: 0 auto 25px;
        margin-top: 50px;
        -moz-border-radius: 2px;
        -webkit-border-radius: 2px;
        border-radius: 2px;
        -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    }

    .profile-img-card {
        width: 96px;
        height: 96px;
        margin: 0 auto 10px;
        display: block;
        -moz-border-radius: 50%;
        -webkit-border-radius: 50%;
        border-radius: 50%;
    }
</style>