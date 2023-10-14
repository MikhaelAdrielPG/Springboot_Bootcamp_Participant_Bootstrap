$(document).ready(function() {
    const participantTableBody = $('#participant-table-body');
    const participantForm = $('#participant-form');

    function loadParticipants() {
        $.get('/participants', function(data) {
         participantTableBody.empty();
          data.data.forEach(participant => {
            const row = `<tr>
                    <td>${participant.name}</td>
                    <td>${participant.address}</td>
                    <td>${participant.phone}</td>
                </tr>`;
            participantTableBody.append(row);
          });
        });
    }

    participantForm.on('submit', function(event) {
        event.preventDefault();
        const name = $('#name').val();
        const address = $('#address').val();
        const phone = $('#phone').val();

        // Mengirim data peserta baru ke backend
        $.ajax({
            url: '/participants',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                name: name,
                address: address,
                phone: phone,
            }),
            success: function(newParticipant) {
                loadParticipants();
            }
        });
    });
    loadParticipants();
});