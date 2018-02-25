//
//  NewAddVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/13/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import Photos
import YangMingShan
import DropDown
import Alamofire
import KMPlaceholderTextView
import SkyFloatingLabelTextField
import IQDropDownTextField

class NewAddVC: BaseTVC, UICollectionViewDelegate,UICollectionViewDataSource,UICollectionViewDelegateFlowLayout, UITextViewDelegate,YMSPhotoPickerViewControllerDelegate,IQDropDownTextFieldDelegate {
    
    @IBOutlet weak var collectionView : UICollectionView!
    
    let cellIdentifier = "imageCellIdentifier"
    var images: NSArray! = []
    
    var imagesPaths: [String] = []
    
    @IBOutlet var tfields: [SkyFloatingLabelTextField]!
    @IBOutlet var tfields2: [IQDropDownTextField]!

    
    @IBOutlet weak var tfTitle : UITextField!
    @IBOutlet weak var tfLocation : IQDropDownTextField!
    @IBOutlet weak var tfCategory : UITextField!
    @IBOutlet weak var tfPrice : SkyFloatingLabelTextField!
    @IBOutlet weak var negotiableSwitch : UISwitch!
    @IBOutlet weak var tfDescription : KMPlaceholderTextView!
    @IBOutlet weak var tfType : IQDropDownTextField!
    @IBOutlet weak var tfModel : IQDropDownTextField!
    
    var locations = [Location]()
    var typesBase = [Type]()
    var types = [Type]()
    
    
    var selectedCategory : Cat!{
        didSet{
            if self.tfCategory != nil{
                self.tfCategory.text = selectedCategory.category_name
                self.setupTypes()
            }
        }
    }

    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        getData()
        setupViews()
    }
    
    
    override func getRefreshing() {
        Communication.shared.get_data_lists { (locations, types, educations, schedules) in
            self.hideLoading()
            
            self.typesBase = types
            self.locations = locations
            self.setupLocations()
        }
    }
    
    func setupViews(){
        
        tfDescription.placeholder = "Description"

        // CollectionView
        self.collectionView.delegate = self
        self.collectionView.dataSource = self
        
        // Background
        let img = UIImageView.init(image: #imageLiteral(resourceName: "Background-blur"))
        img.contentMode = .scaleAspectFill
        img.clipsToBounds = true
        self.tableView.backgroundView = img
        
        // Navigation Item
        self.navigationItem.rightBarButtonItem = UIBarButtonItem.init(title: "Reset", style: .plain, target: self, action: #selector(self.reset))
        self.title = "Add new"
        

        // IQDropDownTextField
        for i in tfields{
            i.lineColor = .clear
            i.lineHeight = 0
            i.selectedLineColor = .clear
            i.selectedLineHeight = 0
            
            i.selectedTitleColor = Theme.Color.White
            i.placeholderColor = Theme.Color.White
            i.titleColor = Theme.Color.White
            i.textColor = .white
            
            if let place = i.placeholder{
                let arr = place.components(separatedBy: "*")
                if let temp = arr.first{
                    i.placeholder = temp.localized + "*"
                }
            }
        }
        
        for i in tfields2{
            
            i.placeHolderColor = Theme.Color.White
            
            
            if let place = i.placeholder{
                let arr = place.components(separatedBy: "*")
                if let temp = arr.first{
                    i.placeholder = temp.localized + "*"
                }
            }
        }
        
        
    }
    
    
    func textField(_ textField: IQDropDownTextField, didSelectItem item: String?) {
        
        if textField == tfType{
            self.setupModels()
        }
        
    }
    
    
    
    
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        switch indexPath.section {
        case 0:
            return (indexPath.row == 0 || indexPath.row == 6) ? UITableViewAutomaticDimension : 50
        default:
            return UITableViewAutomaticDimension
        }
    }
    
    func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool {
        
        
        let size = textView.bounds.size
        let newSize = textView.sizeThatFits(CGSize(width: size.width,
                                                   height: CGFloat.maximum(size.width, size.height)))
        
        // Resize the cell only when cell's size is changed
        if size.height != newSize.height {
            UIView.setAnimationsEnabled(false)
            tableView?.beginUpdates()
            tableView?.endUpdates()
            UIView.setAnimationsEnabled(true)
            
            let thisIndexPath = IndexPath.init(row: 6, section: 0)
            
            tableView.scrollToRow(at: thisIndexPath, at: .bottom, animated: false)
        }
        
        return true
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if indexPath.section == 0,indexPath.row == 2{
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "ChooseCatVC2") as! ChooseCatVC
            
            let c = Cat()
            c.category_name = "ChooseCategory".localized
            c.children = Provider.shared.cats
            
            vc.cat = c
            vc.newAdd = self
            vc.modalPresentationStyle = .overCurrentContext
            vc.modalTransitionStyle = .crossDissolve
            self.present(vc, animated: true, completion: nil)
        }
        if indexPath.section == 0,indexPath.row == 3{
            //            self.locationDropDown.show()
        }
    }
    
    override func tableView(_ tableView: UITableView, willDisplayHeaderView view: UIView, forSection section: Int) {
        (view as! UITableViewHeaderFooterView).backgroundView?.backgroundColor = UIColor.clear
    }
    
    override func tableView(_ tableView: UITableView, willDisplayFooterView view: UIView, forSection section: Int) {
        (view as! UITableViewHeaderFooterView).backgroundView?.backgroundColor = UIColor.clear
    }
    
    @objc func reset(){
        print(self.imagesPaths)
    }
    
    func isValidForm() -> Bool{
        return true
    }
    
    func validMessage(_ message : String){
        self.showErrorMessage(text: message)
    }
    
    @IBAction func submitAction(){
        
        guard let titleAd = self.tfTitle.text, !titleAd.isEmpty else {
            self.validMessage("Please enter title")
            return
        }
        guard let cat = self.selectedCategory, let category_id = cat.category_id else {
            self.validMessage("Please select category")
            return
        }
        guard let loc = self.selectedLocation, let location_id = loc.location_id else {
            self.validMessage("Please select category")
            return
        }
        guard let desAd = self.tfDescription.text, !desAd.isEmpty else {
            self.validMessage("Please enter description")
            return
        }
        guard let price = self.tfPrice.text, !price.isEmpty, (Int(price) != nil) else {
            self.validMessage("Please enter price")
            return
        }
        guard  !self.imagesPaths.isEmpty else {
            self.validMessage("Please add images")
            return
        }
        
        var params : [String : Any] = [:]
        params["is_negotiable"] = self.negotiableSwitch.isOn ? 1 : 0
        
        self.showLoading()
        Communication.shared.post_new_ad(category_id: category_id.intValue, location_id: location_id.intValue, show_period: 1, title: titleAd, description: desAd, price: price, images: self.imagesPaths,paramsAdditional : params) { (res) in
            self.hideLoading()
            
            
        }
        
    }
    
}

//MARK: CollectionView
extension NewAddVC{
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 8
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! CommericalCell
        
        let img : UIImage! = (images != nil && indexPath.row < images.count) ?  self.images.object(at: indexPath.row) as? UIImage : nil
        cell.newAddVC = self
        cell.imageNew = (indexPath.row,img)
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let w = collectionView.frame.width - 12
        return CGSize(width: w / 4, height: w / 4)
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        print(indexPath.row + 1)
        
        if indexPath.row >= self.images.count{
            let pickerViewController = YMSPhotoPickerViewController.init()
            
            var cnt : Int = 8
            cnt -= (self.images != nil) ? self.images.count  : 0
            pickerViewController.numberOfPhotoToSelect = UInt(cnt)
            
            pickerViewController.theme.titleLabelTextColor = Theme.Color.White
            pickerViewController.theme.navigationBarBackgroundColor = Theme.Color.red
            pickerViewController.theme.tintColor = Theme.Color.White
            pickerViewController.theme.orderTintColor = Theme.Color.red
            pickerViewController.theme.orderLabelTextColor = Theme.Color.White
            pickerViewController.theme.cameraVeilColor = Theme.Color.red
            pickerViewController.theme.cameraIconColor = UIColor.white
            pickerViewController.theme.statusBarStyle = .lightContent
            
            self.yms_presentCustomAlbumPhotoView(pickerViewController, delegate: self)
        }else{
            let alert = UIAlertController.init(title: "Would you like to:", message: "", preferredStyle: .actionSheet)
            
            
            alert.addAction(UIAlertAction.init(title: "Make as Main", style: .default, handler: { (ac) in
                
                let mutableImages: NSMutableArray! = NSMutableArray.init(array: self.images)
                let temp = mutableImages.object(at: indexPath.row)
                mutableImages.removeObject(at: indexPath.row)
                mutableImages.insert(temp, at: 0)
                self.images = NSArray.init(array: mutableImages)
                
                if indexPath.row < self.imagesPaths.count{
                    let temp2 = self.imagesPaths[indexPath.row]
                    self.imagesPaths.remove(at: indexPath.row)
                    self.imagesPaths.insert(temp2, at: indexPath.row)
                }
                
                self.collectionView.reloadData()
            }))
            
            
            alert.addAction(UIAlertAction.init(title: "Delete Photo", style: .destructive, handler: { (ac) in
                
                self.deletePhotoImage(indexPath.row)
                
                
                self.collectionView.reloadData()
            }))
            
            
            
            alert.addAction(UIAlertAction.init(title: "Cancel".localized, style: .cancel, handler: nil))
            
            self.present(alert, animated: true, completion: nil)
            
        }
    }
}


//MARK: Photos YangMingShan
extension NewAddVC{
    
    @objc func deletePhotoImage(_ index: Int) {
        let mutableImages: NSMutableArray! = NSMutableArray.init(array: images)
        mutableImages.removeObject(at: index)
        self.images = NSArray.init(array: mutableImages)
        
        if index < self.imagesPaths.count{
            self.imagesPaths.remove(at: index)
        }
        
        self.collectionView.reloadData()
    }
    
    // MARK: - YMSPhotoPickerViewControllerDelegate
    
    func photoPickerViewControllerDidReceivePhotoAlbumAccessDenied(_ picker: YMSPhotoPickerViewController!) {
        let alertController = UIAlertController.init(title: "Allow photo album access?", message: "Need your permission to access photo albumbs", preferredStyle: .alert)
        let dismissAction = UIAlertAction.init(title: "Cancel", style: .cancel, handler: nil)
        let settingsAction = UIAlertAction.init(title: "Settings", style: .default) { (action) in
            UIApplication.shared.openURL(URL.init(string: UIApplicationOpenSettingsURLString)!)
        }
        alertController.addAction(dismissAction)
        alertController.addAction(settingsAction)
        
        self.present(alertController, animated: true, completion: nil)
    }
    
    func photoPickerViewControllerDidReceiveCameraAccessDenied(_ picker: YMSPhotoPickerViewController!) {
        let alertController = UIAlertController.init(title: "Allow camera album access?", message: "Need your permission to take a photo", preferredStyle: .alert)
        let dismissAction = UIAlertAction.init(title: "Cancel", style: .cancel, handler: nil)
        let settingsAction = UIAlertAction.init(title: "Settings", style: .default) { (action) in
            UIApplication.shared.openURL(URL.init(string: UIApplicationOpenSettingsURLString)!)
        }
        alertController.addAction(dismissAction)
        alertController.addAction(settingsAction)
        
        // The access denied of camera is always happened on picker, present alert on it to follow the view hierarchy
        picker.present(alertController, animated: true, completion: nil)
    }
    
    func photoPickerViewController(_ picker: YMSPhotoPickerViewController!, didFinishPicking image: UIImage!) {
        picker.dismiss(animated: true) {
            self.images = [image]
            self.collectionView.reloadData()
        }
    }
    
    func photoPickerViewController(_ picker: YMSPhotoPickerViewController!, didFinishPickingImages photoAssets: [PHAsset]!) {
        
        picker.dismiss(animated: true) {
            let imageManager = PHImageManager.init()
            let options = PHImageRequestOptions.init()
            options.deliveryMode = .highQualityFormat
            options.resizeMode = .exact
            options.isSynchronous = true
            
            let mutableImages: NSMutableArray! = []
            
//            let w = self.collectionView.frame.width - 12
//            let customSize = CGSize(width: w / 4, height: w / 4)
            
            for i in self.images{
                mutableImages.add(i)
            }
            
            for asset: PHAsset in photoAssets
            {
                
                imageManager.requestImageData(for: asset, options: options, resultHandler: { (dat, ss, oo, rr) in
                    self.imagesPaths.append("")
                    mutableImages.add(UIImage.init(data: dat!)!)
                })
                
            }
            
            
            self.images = mutableImages.copy() as? NSArray
            self.collectionView.reloadData()
        }
    }
    
    
    /*func uploadImage(_ img : UIImage){
        
        let path = savePhotoLocal(img)
        
        Alamofire.upload(
            multipartFormData: { multipartFormData in
                
                if let p = path{
                    multipartFormData.append(p, withName: "image")
                }else{
                    multipartFormData.append("".getData, withName: "image")
                }
                
        },
            to: "http://192.168.9.53/Dealat/index.php/api/ads_control/ad_images_upload/format/json" ,headers : Communication.shared.getHearders(),
            encodingCompletion: { encodingResult in
                
                switch encodingResult {
                case .success(let upload, _, _):
                    
                    upload.uploadProgress(closure: { (Progress) in
                        print("Upload Progress: \(Progress.fractionCompleted)")
                        if Progress.fractionCompleted == 1{
                        }
                    })
                    
                    upload.responseObject { (response : DataResponse<CustomResponse>) in
                        debugPrint(response)
                        
                        Communication.shared.output(response)
                        self.hideLoading()
                        
                        switch response.result{
                        case .success(let value):
                            
                            if value.status{
                                
                                
                            }else{
                                notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                            }
                            break
                        case .failure(let error):
                            print(error.localizedDescription)
                            notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                            break
                        }
                        
                    }
                case .failure(let encodingError):
                    self.hideLoading()
                    print(encodingError)
                }
        })
        
        
    }
    
    func savePhotoLocal(_ img : UIImage) -> URL?{
        let tt = img.resized(toWidth: 512)
        
        var imageData = UIImagePNGRepresentation(tt!)
        
        let imageSize: Int = imageData!.count
        print("size of image in KB: %f ", imageSize / 1024)
        
        if (imageSize / 1024) > 2000 {
            
            return nil
        }
        
        if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
            
            let path = dir.appendingPathComponent("img.png")
            
            //writing
            do {
                try imageData!.write(to: path, options: Data.WritingOptions.atomic)
            }
            catch {}
        }
        
        let rrr = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first!
        
        return  rrr.appendingPathComponent("img.png")
    }*/
    
    
}

// IQTEXTFIELD
extension NewAddVC{
    
    
    var selectedLocation : Location!{
        let row = self.tfLocation.selectedRow
        if row != -1, self.locations.count > row{
            return self.locations[row]
        }
        return nil
    }
    
    var selectedType : Type!{
        let row = self.tfType.selectedRow
        if row != -1, self.types.count > row{
            return self.types[row]
        }
        return nil
    }
    
    
    var selectedModel : Model!{
        let row = self.tfModel.selectedRow
        if row != -1, self.selectedType.models.count > row{
            return self.selectedType.models[row]
        }
        return nil
    }
    
    
    func setupLocations(){
        let list = self.locations.map({"\($0.city_name!) - \($0.location_name!)"})
        tfLocation.itemList = list
        tfLocation.itemListUI = list
        tfLocation.dropDownMode = .textPicker
        tfLocation.isOptionalDropDown = true
        tfLocation.delegate = self
    }
    
    
    func setupModels(){
        let list = self.selectedType.models.map({"\($0.name!)"})
        tfModel.itemList = list
        tfModel.itemListUI = list
        tfModel.dropDownMode = .textPicker
        tfModel.isOptionalDropDown = true
        tfModel.delegate = self
    }
    
    func setupTypes(){
        types = typesBase.filter({$0.tamplate_id.intValue == self.selectedCategory.tamplate_id.intValue})
        
        let list = self.types.map({"\($0.name!)"})
        tfType.itemList = list
        tfType.itemListUI = list
        tfType.dropDownMode = .textPicker
        tfType.isOptionalDropDown = true
        tfType.delegate = self
    }

    
}
