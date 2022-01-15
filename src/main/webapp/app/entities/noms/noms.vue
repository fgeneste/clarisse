<template>
  <div>
    <h2 id="page-heading" data-cy="NomsHeading">
      <span v-text="$t('clarisseApp.noms.home.title')" id="noms-heading">Noms</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('clarisseApp.noms.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'NomsCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-noms">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('clarisseApp.noms.home.createLabel')"> Create a new Noms </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && noms && noms.length === 0">
      <span v-text="$t('clarisseApp.noms.home.notFound')">No noms found</span>
    </div>
    <div class="table-responsive" v-if="noms && noms.length > 0">
      <table class="table table-striped" aria-describedby="noms">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.noms.qualite')">Qualite</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.noms.nomUsuel')">Nom Usuel</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.noms.prenomUsuel')">Prenom Usuel</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.noms.nomPatronymique')">Nom Patronymique</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.noms.prenomCivil')">Prenom Civil</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.noms.nomMarital')">Nom Marital</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.noms.nomDistinctif')">Nom Distinctif</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.noms.nomMajuscule')">Nom Majuscule</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.noms.nomTechnique')">Nom Technique</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.noms.feminisation')">Feminisation</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.noms.dateDebut')">Date Debut</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.noms.dateFin')">Date Fin</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.noms.personne')">Personne</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="noms in noms" :key="noms.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'NomsView', params: { nomsId: noms.id } }">{{ noms.id }}</router-link>
            </td>
            <td>{{ noms.qualite }}</td>
            <td>{{ noms.nomUsuel }}</td>
            <td>{{ noms.prenomUsuel }}</td>
            <td>{{ noms.nomPatronymique }}</td>
            <td>{{ noms.prenomCivil }}</td>
            <td>{{ noms.nomMarital }}</td>
            <td>{{ noms.nomDistinctif }}</td>
            <td>{{ noms.nomMajuscule }}</td>
            <td>{{ noms.nomTechnique }}</td>
            <td>{{ noms.feminisation }}</td>
            <td>{{ noms.dateDebut }}</td>
            <td>{{ noms.dateFin }}</td>
            <td>
              <div v-if="noms.personne">
                <router-link :to="{ name: 'PersonneView', params: { personneId: noms.personne.id } }">{{ noms.personne.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'NomsView', params: { nomsId: noms.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'NomsEdit', params: { nomsId: noms.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(noms)"
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
        ><span id="clarisseApp.noms.delete.question" data-cy="nomsDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-noms-heading" v-text="$t('clarisseApp.noms.delete.question', { id: removeId })">
          Are you sure you want to delete this Noms?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-noms"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeNoms()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./noms.component.ts"></script>
