<template>
    <div>
        <RequestInfo v-bind:request="request"/>
        <h4 class="mt-2">Оборудование в заявке</h4>
        <table class="table table-sm table-hover table-responsive-sm">
            <thead class="thead-dark">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Штрихкод</th>
                <th scope="col">Название</th>
                <th scope="col">Комментарий</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <tr class=""
                :class="{ active: index == currentIndex }"
                v-for="(equipment, index) in request.equipment"
                :key="index"
            >
                <th class="align-middle">{{ equipment.id }}</th>
                <td class="align-middle">{{ equipment.barcode }}</td>
                <td class="align-middle">{{ equipment.name }}</td>
                <td class="align-middle">{{ equipment.comment }}</td>
                <td class="d-flex justify-content-end">
                    <a class="btn btn-warning btn-sm"
                       :href="'/equipments/' + equipment.id"
                    >
                        <font-awesome-icon icon="edit"/>
                    </a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import RequestInfo from "./RequestInfo";

    export default {
        name: "RequestEquipmentList",
        components: {RequestInfo},
        props: ['request'],
        data() {
            return {
                currentIndex: -1,
            };
        },
    }
</script>

<style scoped>

</style>