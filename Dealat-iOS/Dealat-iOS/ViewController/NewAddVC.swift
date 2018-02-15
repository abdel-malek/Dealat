//
//  NewAddVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/13/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import TLPhotoPicker
import Photos

class NewAddVC: BaseTVC, UICollectionViewDelegate,UICollectionViewDataSource,UICollectionViewDelegateFlowLayout, UITextViewDelegate {

    @IBOutlet weak var collectionView : UICollectionView!
//    var selectedAssets = [TLPHAsset]()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        setupViews()
    }
    
    func setupViews(){
        self.collectionView.delegate = self
        self.collectionView.dataSource = self
    }

    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 8
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! CommericalCell
        
        cell.tag = indexPath.row
        cell.newAdd = true
        
        return cell
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let w = collectionView.frame.width - 12
        return CGSize(width: w / 4, height: w / 4)
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        print(indexPath.row)
        
        
//        let viewController = CustomPhotoPickerViewController()
//        viewController.delegate = self
//        viewController.didExceedMaximumNumberOfSelection = { [weak self] (picker) in
//            self?.showExceededMaximumAlert(vc: picker)
//        }
//        var configure = TLPhotosPickerConfigure()
//        configure.numberOfColumn = 3
//        if #available(iOS 10.2, *) {
//            configure.cameraCellNibSet = (nibName: "CustomCameraCell", bundle: Bundle.main)
//        }
//        viewController.configure = configure
//        viewController.selectedAssets = self.selectedAssets
//        self.present(viewController.wrapNavigationControllerWithoutBar(), animated: true, completion: nil)
        
    }
    
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        switch indexPath.section {
        case 0:
            return UITableViewAutomaticDimension
        default:
            return 50
        }
    }
    
    func textViewDidChange(_ textView: UITextView) {
        
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
            
            let thisIndexPath = IndexPath.init(row: 2, section: 1)
            
            tableView.scrollToRow(at: thisIndexPath, at: .bottom, animated: false)
        }
        
        return true
    }
}

/*extension NewAddVC : TLPhotosPickerViewControllerDelegate {

    @IBAction func pickerButtonTap() {
        let viewController = TLPhotosPickerViewController()
        viewController.delegate = self
        var configure = TLPhotosPickerConfigure()
        //configure.nibSet = (nibName: "CustomCell_Instagram", bundle: Bundle.main) // If you want use your custom cell..
        self.present(viewController, animated: true, completion: nil)
    }
    //TLPhotosPickerViewControllerDelegate
    func dismissPhotoPicker(withTLPHAssets: [TLPHAsset]) {
        // use selected order, fullresolution image
        self.selectedAssets = withTLPHAssets
    }
    func dismissPhotoPicker(withPHAssets: [PHAsset]) {
        // if you want to used phasset.
    }
    func photoPickerDidCancel() {
        // cancel
    }
    func dismissComplete() {
        // picker viewcontroller dismiss completion
    }
    func didExceedMaximumNumberOfSelection(picker: TLPhotosPickerViewController) {
        // exceed max selection
    }
    func handleNoCameraPermissions(picker: TLPhotosPickerViewController) {
        // Handle no camera permissions case
    }
}

public struct TLPHAsset {
    public enum AssetType {
        case photo,video,livePhoto
    }
    // phasset
    public var phAsset: PHAsset? = nil
    // selected order index
    public var selectedOrder: Int = 0
    // asset type
    public var type: AssetType
    // get full resolution image
    public var fullResolutionImage: UIImage?
    // get photo file size (async)
    public func photoSize(options: PHImageRequestOptions? = nil ,completion: @escaping ((Int)->Void), livePhotoVideoSize: Bool = false)
    // get video file size (async)
    public func videoSize(options: PHVideoRequestOptions? = nil, completion: @escaping ((Int)->Void))
    // get async icloud image (download)
    @discardableResult
    public func cloudImageDownload(progressBlock: @escaping (Double) -> Void, completionBlock:@escaping (UIImage?)-> Void ) -> PHImageRequestID?
    // get original media file async copy temporary media file ( photo(png,gif...etc.) and video ) -> Don't forget, You should delete temporary file.
    public func tempCopyMediaFile(progressBlock:((Double) -> Void)? = nil, completionBlock:@escaping ((URL,String) -> Void)) -> PHImageRequestID?
    // get original asset file name
    public var originalFileName: String?
}
*/
