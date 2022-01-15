<template>
  <div>
    <h2 id="page-heading" data-cy="PersonneHeading">
      <span v-text="$t('clarisseApp.personne.home.title')" id="personne-heading">Personnes</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('clarisseApp.personne.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PersonneCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-personne"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('clarisseApp.personne.home.createLabel')"> Create a new Personne </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && personnes && personnes.length === 0">
      <span v-text="$t('clarisseApp.personne.home.notFound')">No personnes found</span>
    </div>
    <div class="table-responsive" v-if="personnes && personnes.length > 0">
      <table class="table table-striped" aria-describedby="personnes">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.personne.matricule')">Matricule</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.personne.dateDeNaissance')">Date De Naissance</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.personne.lieuDeNaissance')">Lieu De Naissance</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.personne.departementDeNaissance')">Departement De Naissance</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.personne.dateDeDeces')">Date De Deces</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.personne.lieuDeDeces')">Lieu De Deces</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.personne.departementDeDeces')">Departement De Deces</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.personne.profession')">Profession</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.personne.diplome')">Diplome</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.personne.decoration')">Decoration</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.personne.rangProtocolaire')">Rang Protocolaire</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.personne.cspInsee')">Csp Insee</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="personne in personnes" :key="personne.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PersonneView', params: { personneId: personne.id } }">{{ personne.id }}</router-link>
            </td>
            <td>{{ personne.matricule }}</td>
            <td>{{ personne.dateDeNaissance }}</td>
            <td>{{ personne.lieuDeNaissance }}</td>
            <td>{{ personne.departementDeNaissance }}</td>
            <td>{{ personne.dateDeDeces }}</td>
            <td>{{ personne.lieuDeDeces }}</td>
            <td>{{ personne.departementDeDeces }}</td>
            <td>{{ personne.profession }}</td>
            <td>{{ personne.diplome }}</td>
            <td>{{ personne.decoration }}</td>
            <td>{{ personne.rangProtocolaire }}</td>
            <td>{{ personne.cspInsee }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PersonneView', params: { personneId: personne.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PersonneEdit', params: { personneId: personne.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(personne)"
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
        ><span id="clarisseApp.personne.delete.question" data-cy="personneDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-personne-heading" v-text="$t('clarisseApp.personne.delete.question', { id: removeId })">
          Are you sure you want to delete this Personne?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-personne"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePersonne()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./personne.component.ts"></script>
