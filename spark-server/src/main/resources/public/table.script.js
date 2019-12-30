let tableBody = document.querySelector(".data-table tbody");

let projects = [
    {
        name: 'Uber for Cats',
        budget: '$1,000,000',
        lead: 'Jane Smith'
    },
    {
        name: 'Twitter for Puppies',
        budget: '$10,000,000',
        lead: 'John Doe'
    },
    {
        name: 'Lyft for Fish',
        budget: '$15,000,000',
        lead: 'Sally Stevenson'
    }
];

projects.forEach(project => {
    let tableRow = `
    <tr>
      <td class="pa3 bb b--black-10">
        ${project.name}
      </td>
      <td class="pa3 bb b--black-10">
        ${project.budget}
      </td>
      <td class="pa3 bb b--black-10">
        ${project.lead}
      </td>
    </tr>
  `

    tableBody.insertAdjacentHTML("beforeend", tableRow)
});