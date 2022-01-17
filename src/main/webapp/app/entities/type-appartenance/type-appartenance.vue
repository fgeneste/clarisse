<template>
  <div>
    <h2 id="page-heading" data-cy="TypeAppartenanceHeading">
      <span v-text="$t('clarisseApp.typeAppartenance.home.title')" id="type-appartenance-heading">Type Appartenances</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('clarisseApp.typeAppartenance.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TypeAppartenanceCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-type-appartenance"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('clarisseApp.typeAppartenance.home.createLabel')"> Create a new Type Appartenance </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && typeAppartenances && typeAppartenances.length === 0">
      <span v-text="$t('clarisseApp.typeAppartenance.home.notFound')">No typeAppartenances found</span>
    </div>
    <div class="table-responsive" v-if="typeAppartenances && typeAppartenances.length > 0">
      <table class="table table-striped" aria-describedby="typeAppartenances">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.typeAppartenance.typeAppartenance')">Type Appartenance</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.typeAppartenance.libelle')">Libelle</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="typeAppartenance in typeAppartenances" :key="typeAppartenance.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TypeAppartenanceView', params: { typeAppartenanceId: typeAppartenance.id } }">{{
                typeAppartenance.id
              }}</router-link>
            </td>
            <td>{{ typeAppartenance.typeAppartenance }}</td>
            <td>{{ typeAppartenance.libelle }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'TypeAppartenanceView', params: { typeAppartenanceId: typeAppartenance.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'TypeAppartenanceEdit', params: { typeAppartenanceId: typeAppartenance.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(typeAppartenance)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="clarisseApp.typeAppartenance.delete.question"
          data-cy="typeAppartenanceDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-typeAppartenance-heading" v-text="$t('clarisseApp.typeAppartenance.delete.question', { id: removeId })">
          Are you sure you want to delete this Type Appartenance?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-typeAppartenance"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTypeAppartenance()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./type-appartenance.component.ts"></script>
